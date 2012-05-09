package io.trumpet.jdbi;

import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.IDBI;
import org.skife.jdbi.v2.TransactionCallback;
import org.skife.jdbi.v2.TransactionStatus;
import org.skife.jdbi.v2.exceptions.CallbackFailedException;
import org.skife.jdbi.v2.sqlobject.SqlObjectBuilder;
import org.skife.jdbi.v2.tweak.HandleCallback;

/**
 * A delegating DBI that allows changing the actual IDBI implementation.
 *
 * If you set the wrapping c'tor argument to true, every Handle handed out by the delegate will be wrapped in a call to wrapHandle().
 */
public class DbiAdapter implements IDBI
{
    private final boolean wrapping;

    private volatile IDBI delegate = null;

    public DbiAdapter()
    {
        this.wrapping = false;
    }

    public DbiAdapter(final IDBI delegate)
    {
        this.wrapping = false;
        this.delegate = delegate;
    }

    public DbiAdapter(final boolean wrapping)
    {
        this.wrapping = wrapping;
    }

    public DbiAdapter(final IDBI delegate, final boolean wrapping)
    {
        this.delegate = delegate;
        this.wrapping = wrapping;
    }

    public IDBI getDelegate()
    {
        return delegate;
    }

    public boolean isWrapping()
    {
        return wrapping;
    }

    public void setDelegate(final IDBI delegate)
    {
        this.delegate = delegate;
    }

    /**
     * This allows subclasses to intercept the Handle object and act on it.
     */
    protected Handle wrapHandle(final Handle handle)
    {
        return handle;
    }

    @Override
    public void define(String key, Object value)
    {
        if (delegate == null) {
            throw new IllegalStateException("No delegate has been set!");
        }

        delegate.define(key, value);
    }

    @Override
    public Handle open()
    {
        if (delegate == null) {
            throw new IllegalStateException("No delegate has been set!");
        }

        return wrapping ? wrapHandle(delegate.open()) : delegate.open();
    }

    @Override
    public <ReturnType> ReturnType inTransaction(final TransactionCallback<ReturnType> callback) throws CallbackFailedException
    {
        if (delegate == null) {
            throw new IllegalStateException("No delegate has been set!");
        }

        if (!wrapping) {
            return delegate.inTransaction(callback);
        }
        else {
            return delegate.inTransaction(new TransactionCallback<ReturnType>() {
                @Override
                public ReturnType inTransaction(final Handle handle, final TransactionStatus transactionStatus) throws Exception
                {
                    return callback.inTransaction(wrapHandle(handle), transactionStatus);
                }
            });
        }
    }

    @Override
    public <ReturnType> ReturnType withHandle(final HandleCallback<ReturnType> callback) throws CallbackFailedException
    {
        if (delegate == null) {
            throw new IllegalStateException("No delegate has been set!");
        }

        if (!wrapping) {
            return delegate.withHandle(callback);
        }
        else {
            return delegate.withHandle(new HandleCallback<ReturnType>() {
                @Override
                public ReturnType withHandle(final Handle handle) throws Exception
                {
                    return callback.withHandle(wrapHandle(handle));
                }
            });
        }
    }

    @Override
    public <SqlObjectType> SqlObjectType open(final Class<SqlObjectType> sqlObjectType)
    {
        if (delegate == null) {
            throw new IllegalStateException("No delegate has been set!");
        }

        return wrapping ? SqlObjectBuilder.open(this, sqlObjectType) :  delegate.open(sqlObjectType);
    }

    @Override
    public <SqlObjectType> SqlObjectType onDemand(final Class<SqlObjectType> sqlObjectType)
    {
        if (delegate == null) {
            throw new IllegalStateException("No delegate has been set!");
        }

        return wrapping ? SqlObjectBuilder.onDemand(this, sqlObjectType) : delegate.onDemand(sqlObjectType);
    }

    @Override
    public void close(final Object sqlObject)
    {
        if (delegate == null) {
            throw new IllegalStateException("No delegate has been set!");
        }

        delegate.close(sqlObject);
    }
}
