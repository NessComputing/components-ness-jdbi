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

package com.nesscomputing.jdbi.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.joda.time.DateTime;
import org.skife.jdbi.v2.util.TypedMapper;

import com.nesscomputing.jdbi.JdbiMappers;

/**
 * A DateTime mapper, suitable for use with the "interface-style"
 * JDBI API.
 *
 * Example usage:
 * public interface FooDao {
 *  @SqlQuery("SELECT created_dt FROM foo LIMIT 1")
 *  @Mapper(DateTimeMapper.class)
 *  DateTime getSingleTime();
 * }
 *
 */
public class DateTimeMapper extends TypedMapper<DateTime> {
    @Override
    protected DateTime extractByName(ResultSet r, String name) throws SQLException {
        return JdbiMappers.getDateTime(r, name);
    }

    @Override
    protected DateTime extractByIndex(ResultSet r, int index) throws SQLException {
        return JdbiMappers.getDateTime(r, index);
    }
}
