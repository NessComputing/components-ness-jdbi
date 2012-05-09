package io.trumpet.jdbi.argument;

import java.net.URI;
import java.sql.Array;
import java.sql.Types;
import java.util.Collection;

import org.joda.time.DateTime;

public final class JdbiArguments
{
    private JdbiArguments()
    {
    }

    public static final <U extends Enum<U>> EnumArgument<U> forEnum(final Enum<U> enumValue)
    {
    	return new EnumArgument<U>(enumValue);
    }

    /**
     * @deprecated When using ness-sql >= 3.0 and tc-jdbi >= 2.0,
     * install the {@link ArgumentFactoryModule} and bind the URI object directly.
     * JDBI then will figure out the right thing.
     */
    @Deprecated
    public static final URIArgument forURI(final URI uri)
    {
    	return new URIArgument(uri);
    }

    /**
     * @deprecated When using ness-sql >= 3.0 and tc-jdbi >= 2.0,
     * install the {@link ArgumentFactoryModule} and bind the URI object directly.
     * JDBI then will figure out the right thing.
     */
    @Deprecated
    public static final DateTimeArgument forDateTime(final DateTime dateTime)
    {
    	return new DateTimeArgument(dateTime);
    }

    /**
     * @deprecated When using ness-sql >= 3.0 and tc-jdbi >= 2.0,
     * install the {@link ArgumentFactoryModule} and bind the URI object directly.
     * JDBI then will figure out the right thing.
     */
    @Deprecated
    public static final SqlArrayArgument forSqlArray(final Array sqlArray)
    {
        return new SqlArrayArgument(sqlArray);
    }

    public static final CollectionArgument<String> forStringCollection(final Collection<String> collection)
    {
        return new CollectionArgument<String>(collection, Types.VARCHAR);
    }

    public static final CollectionArgument<Integer> forIntCollection(final Collection<Integer> collection)
    {
        return new CollectionArgument<Integer>(collection, Types.INTEGER);
    }

    public static final CollectionArgument<Long> forLongCollection(final Collection<Long> collection)
    {
        return new CollectionArgument<Long>(collection, Types.BIGINT);
    }

    public static final <T> CollectionArgument<T> forObjectCollection(final Collection<? extends T> collection, final String type)
    {
        return new CollectionArgument<T>(collection, type);
    }
}

