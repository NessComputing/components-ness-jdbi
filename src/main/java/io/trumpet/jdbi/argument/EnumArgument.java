package io.trumpet.jdbi.argument;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.Argument;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;


/**
 * JDBI converter to bind an Enum
 */
public class EnumArgument<T extends Enum<T>> implements Argument
{
    private final Enum<T> enumValue;

    EnumArgument(final Enum<T> enumValue)
    {
        this.enumValue = enumValue;
        }

    public static <X extends Enum<X>> EnumArgument<X> from(X enumValue) {
        return new EnumArgument<X>(enumValue);
    }

    @Override
    public void apply(final int position,
                      final PreparedStatement statement,
                      final StatementContext ctx) throws SQLException
    {
        if (enumValue == null) {
            statement.setNull(position, Types.NULL);
        }
        else {
            statement.setString(position, enumValue.toString());
        }
    }

    @Override
    public String toString()
    {
        return enumValue.toString();
    }
}
