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

import org.joda.time.ReadableInstant;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.Argument;
import org.skife.jdbi.v2.tweak.ArgumentFactory;

import java.net.URI;
import java.sql.Array;

@SuppressWarnings("PMD.MissingStaticMethodInNonInstantiatableClass")
final class ArgumentFactories
{
    private ArgumentFactories()
    {
    }

    static final class UriArgumentFactory implements ArgumentFactory<URI>
    {
        @Override
        public boolean accepts(final Class<?> expectedType, final Object value, final StatementContext ctx)
        {
            return value instanceof URI;
        }

        @Override
        public Argument build(final Class<?> expectedType, final URI value, final StatementContext ctx)
        {
            return new URIArgument(value);
        }
    }

    static final class DateTimeArgumentFactory implements ArgumentFactory<ReadableInstant>
    {
        @Override
        public boolean accepts(final Class<?> expectedType, final Object value, final StatementContext ctx)
        {
            return value instanceof ReadableInstant;
        }

        @Override
        public Argument build(final Class<?> expectedType, final ReadableInstant value, final StatementContext ctx)
        {
            return new DateTimeArgument(value);
        }
    }

    static final class SqlArrayArgumentFactory implements ArgumentFactory<Array>
    {
        @Override
        public boolean accepts(final Class<?> expectedType, final Object value, final StatementContext ctx)
        {
            return value instanceof Array;
        }

        @Override
        public Argument build(final Class<?> expectedType, final Array value, final StatementContext ctx)
        {
            return new SqlArrayArgument(value);
        }
    }

    static final class ArgumentArgumentFactory implements ArgumentFactory<Argument>
    {
        @Override
        public boolean accepts(final Class<?> expectedType, final Object value, final StatementContext ctx)
        {
            return value instanceof Argument;
        }

        @Override
        public Argument build(final Class<?> expectedType, final Argument value, final StatementContext ctx)
        {
            return value;
        }
    }
}
