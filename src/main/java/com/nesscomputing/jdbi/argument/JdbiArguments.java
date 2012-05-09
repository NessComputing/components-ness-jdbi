/**
 * Copyright (C) 2012 Ness Computing, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.nesscomputing.jdbi.argument;

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

