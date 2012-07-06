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

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.Argument;

import com.nesscomputing.types.PlatformId;


/**
 * JDBI converter to bind a platform id.
 */
public class PlatformIdArgument<T> implements Argument
{
    private final PlatformId<T> platformIdValue;

    PlatformIdArgument(final PlatformId<T> platformIdValue)
    {
        this.platformIdValue = platformIdValue;
    }

    public static <X> PlatformIdArgument<X> from(PlatformId<X> platformIdValue) {
        return new PlatformIdArgument<X>(platformIdValue);
    }

    @Override
    public void apply(final int position,
                      final PreparedStatement statement,
                      final StatementContext ctx) throws SQLException
    {
        if (platformIdValue == null) {
            statement.setNull(position, Types.NULL);
        }
        else {
            statement.setString(position, platformIdValue.getId().toString());
        }
    }

    @Override
    public String toString()
    {
        return platformIdValue.toString();
    }
}
