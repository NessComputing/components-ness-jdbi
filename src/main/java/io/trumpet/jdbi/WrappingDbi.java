package io.trumpet.jdbi;

import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.IDBI;
import org.skife.jdbi.v2.tweak.StatementBuilder;
import org.skife.jdbi.v2.tweak.StatementLocator;
import org.skife.jdbi.v2.tweak.StatementRewriter;

/**
 * Wraps a DBI so that the statement locator, builder, or rewriter can be set without affecting the delegate DBI.
 */
public class WrappingDbi extends DbiAdapter implements IDBI
{
    private final StatementLocator locator;
    private final StatementBuilder builder;
    private final StatementRewriter rewriter;

    private WrappingDbi(final IDBI delegate,
    				   final StatementLocator locator,
    				   final StatementBuilder builder,
    				   final StatementRewriter rewriter)
    {
        super(delegate, true);
        this.locator = locator;
        this.builder = builder;
        this.rewriter = rewriter;
    }

    @Override
    protected Handle wrapHandle(final Handle handle)
    {
        if (locator != null) {
            handle.setStatementLocator(locator);
        }

        if (builder != null) {
            handle.setStatementBuilder(builder);
        }

        if (rewriter != null) {
            handle.setStatementRewriter(rewriter);
        }

        return handle;
    }

    public static final class Builder
    {
    	private final IDBI delegate;
        private StatementLocator locator = null;
        private StatementBuilder builder = null;
        private StatementRewriter rewriter = null;

        public Builder(final IDBI delegate)
        {
        	this.delegate = delegate;
        }

        public IDBI build()
        {
        	return new WrappingDbi(delegate, locator, builder, rewriter);
        }

		public Builder setLocator(final StatementLocator locator)
		{
			this.locator = locator;
			return this;
		}

		public Builder setBuilder(final StatementBuilder builder)
		{
			this.builder = builder;
			return this;
		}

		public Builder setRewriter(final StatementRewriter rewriter)
		{
			this.rewriter = rewriter;
			return this;
		}
    }
}
