package io.trumpet.jdbi.argument;


import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.Argument;
import org.skife.jdbi.v2.tweak.ArgumentFactory;

/**
 * Allows registration of ArgumentFactories to accept enums as JDBI arguments.
 */

public class EnumFactoryBuilder
{
    public static <T extends Enum<T>> ArgumentFactory<T> build(final Class<T> clazz)
    {
        return new ArgumentFactory<T>() {

            @Override
            public boolean accepts(final Class<?> expectedType, final Object value, final StatementContext ctx)
            {
                return (clazz.isInstance(value));
            }

            @Override
            public Argument build(final Class<?> expectedType, final T value, final StatementContext ctx)
            {
                return EnumArgument.from(value);
            }
        };
    }
}
