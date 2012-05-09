package io.trumpet.jdbi.argument;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

import org.joda.time.ReadableInstant;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.Argument;

/**
 * JDBI converter to bind a DateTime.
 */
public class DateTimeArgument implements Argument
{
    private final ReadableInstant dateTime;

    DateTimeArgument(final ReadableInstant dateTime)
    {
        this.dateTime = dateTime;
    }

    @Override
    public void apply(final int position,
                      final PreparedStatement statement,
                      final StatementContext ctx) throws SQLException
    {
        if (dateTime == null) {
            statement.setNull(position, Types.TIMESTAMP);
        }
        else {
            final long millis = dateTime.getMillis();
            statement.setTimestamp(position, new Timestamp(millis));
        }
    }

    @Override
    public String toString()
    {
        return String.valueOf(dateTime);
    }
}
