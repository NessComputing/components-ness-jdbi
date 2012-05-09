package com.nesscomputing.jdbi.argument;

import org.joda.time.ReadableInstant;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.Argument;
import org.skife.jdbi.v2.tweak.ArgumentFactory;

import java.net.URI;
import java.sql.Array;

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
