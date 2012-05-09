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

import org.apache.commons.lang3.tuple.Pair;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class PairMapper<U, V> implements ResultSetMapper<Pair<U, V>>
{
    private final ResultSetMapper<U> leftMapper;
    private final ResultSetMapper<V> rightMapper;

    public PairMapper(final ResultSetMapper<U> leftMapper, final ResultSetMapper<V> rightMapper)
    {
        this.leftMapper = leftMapper;
        this.rightMapper = rightMapper;
    }

    @Override
    public Pair<U, V> map(int index, final ResultSet r, final StatementContext ctx) throws SQLException
    {
        final U leftValue = leftMapper.map(index, r, ctx);
        final V rightValue = rightMapper.map(index, r, ctx);

        return Pair.of(leftValue, rightValue);
    }
}
