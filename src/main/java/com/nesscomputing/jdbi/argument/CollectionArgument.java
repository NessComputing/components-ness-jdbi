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

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collection;
import java.util.Map;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.Argument;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;


/**
 * JDBI converter to bind a String collection as a SQL array.
 */
public class CollectionArgument<T> implements Argument
{
    private static final Map<Integer, String> SQL_TYPES = ImmutableMap.of(
                                           Types.BIGINT, "bigint",
                                           Types.INTEGER, "int",
                                           Types.VARCHAR, "varchar");

    private final Collection<? extends T> collection;
    private final int sqlType;
    private final String type;

    CollectionArgument(final Collection<? extends T> collection, final int sqlType)
    {
        this.collection = collection;
        this.sqlType = sqlType;
        this.type = SQL_TYPES.get(sqlType);
        Preconditions.checkArgument(type != null, "No string type for " + sqlType + " found!");
    }

    CollectionArgument(final Collection<? extends T> collection, final String type)
    {
        this.collection = collection;
        this.sqlType = Types.OTHER;
        this.type = type;   // This allows database specific types. Such as, but not limited to, UUID.
    }

    @Override
    public void apply(final int position,
                      final PreparedStatement statement,
                      final StatementContext ctx) throws SQLException
    {
        if (collection == null) {
            statement.setNull(position, sqlType);
        }
        else {
            if (statement.getConnection().getMetaData().getDatabaseProductName().equalsIgnoreCase("h2")) {
                // h2 does not support createArrayOf
                statement.setObject(position, collection.toArray());
            } else {
                final Array sqlArray = ctx.getConnection().createArrayOf(type, collection.toArray());
                statement.setArray(position, sqlArray);
            }
        }
    }

    @Override
    public String toString()
    {
        return collection == null ? "<null>" : collection.toString();
    }
}
