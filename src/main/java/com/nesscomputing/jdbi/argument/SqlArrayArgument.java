package com.nesscomputing.jdbi.argument;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.Argument;


/**
 * JDBI converter to bind a SQL Array
 */
public class SqlArrayArgument implements Argument
{
    private final Array sqlArray;

    SqlArrayArgument(final Array sqlArray)
    {
        this.sqlArray = sqlArray;
    }

    @Override
    public void apply(final int position,
                      final PreparedStatement statement,
                      final StatementContext ctx) throws SQLException
    {
        if (sqlArray == null) {
            statement.setNull(position, Types.VARCHAR);
        }
        else {
            statement.setArray(position, sqlArray);
        }
    }

    @Override
    public String toString()
    {
        return sqlArray == null ? "<null>" : sqlArray.toString();
    }
}
