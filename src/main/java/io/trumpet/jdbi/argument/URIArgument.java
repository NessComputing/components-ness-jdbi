package io.trumpet.jdbi.argument;

import java.net.URI;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.Argument;

/**
 * JDBI converter to bind an URI.
 */
public class URIArgument implements Argument
{
    private final URI uri;

    URIArgument(final URI uri)
    {
        this.uri = uri;
    }

    @Override
    public void apply(final int position,
                      final PreparedStatement statement,
                      final StatementContext ctx) throws SQLException
    {
        if (uri == null) {
            statement.setNull(position, Types.VARCHAR);
        }
        else {
            statement.setString(position, uri.toString());
        }
    }

    @Override
    public String toString()
    {
        return String.valueOf(uri);
    }
}
