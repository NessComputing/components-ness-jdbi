package com.nesscomputing.jdbi;

import com.google.common.base.Preconditions;
import org.joda.time.DateTime;

import java.net.URI;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.List;



public final class JdbiMappers
{
    private JdbiMappers()
    {
    }

    /**
     * Returns a Boolean or null if the column was null.
     */
    public static Boolean getBoolean(final ResultSet rs, final String columnName) throws SQLException
    {
        final boolean res = rs.getBoolean(columnName);
        if (rs.wasNull()) {
            return null;
        }
        else {
            return res;
        }
    }

    /**
     * Returns a Boolean or null if the column was null.
     */
    public static Boolean getBoolean(final ResultSet rs, final int columnIndex) throws SQLException
    {
        final boolean res = rs.getBoolean(columnIndex);
        if (rs.wasNull()) {
            return null;
        } else {
            return res;
        }
    }

    /**
     * Returns a DateTime object representing the date or null if the input is null.
     */
    public static DateTime getDateTime(final ResultSet rs, final String columnName) throws SQLException
    {
        final Timestamp ts = rs.getTimestamp(columnName);

        return (ts == null) ? null : new DateTime(ts);
    }

    public static DateTime getDateTime(final ResultSet rs, final int columnIndex) throws SQLException
    {
        final Timestamp ts = rs.getTimestamp(columnIndex);

        return (ts == null) ? null : new DateTime(ts);
    }

    /**
     * Returns an Enum representing the data or null if the input is null.
     */
    public static <T extends Enum<T>> T getEnum(final ResultSet rs, final Class<T> enumType, final String columnName) throws SQLException
    {
        final String str = rs.getString(columnName);

        return (str == null) ? null : Enum.valueOf(enumType, str);
    }

    public static <T extends Enum<T>> T getEnum(final ResultSet rs, final Class<T> enumType, final int columnIndex) throws SQLException
    {
        final String str = rs.getString(columnIndex);

        return (str == null) ? null : Enum.valueOf(enumType, str);
    }

    /**
     * Returns an URI representing the data or null if the input is null.
     */
    public static final URI getURI(final ResultSet rs, final String columnName) throws SQLException
    {
        final String str = rs.getString(columnName);

        return (str == null) ? null : URI.create(str);
    }

    public static final URI getURI(final ResultSet rs, final int columnIndex) throws SQLException
    {
        final String str = rs.getString(columnIndex);

        return (str == null) ? null : URI.create(str);
    }

    /**
     * Returns an array of Strings or null if the input is null. Null is intentional (not empty list), to be
     * able to distinguish between null value and empty list in the db.
     */
    public static final List<String> getStringList(final ResultSet rs, final String columnName) throws SQLException
    {
        final Array ary = rs.getArray(columnName);
        if (ary != null) {
            Preconditions.checkArgument(ary.getBaseType() == Types.VARCHAR || ary.getBaseType() == Types.CHAR);
            return Arrays.asList((String []) ary.getArray());
        }
        return null;
    }

    public static final List<String> getStringList(final ResultSet rs, final int columnIndex) throws SQLException
    {
        final Array ary = rs.getArray(columnIndex);
        if (ary != null) {
            Preconditions.checkArgument(ary.getBaseType() == Types.VARCHAR || ary.getBaseType() == Types.CHAR);
            return Arrays.asList((String []) ary.getArray());
        }
        return null;
    }

    /**
     * Returns an array of Integer or null if the input is null. Null is intentional (not empty list), to be
     * able to distinguish between null value and empty list in the db.
     */
    public static final List<Integer> getIntegerList(final ResultSet rs, final String columnName) throws SQLException
    {
        final Array ary = rs.getArray(columnName);
        if (ary != null) {
            Preconditions.checkArgument(ary.getBaseType() == Types.INTEGER || ary.getBaseType() == Types.SMALLINT);
            return Arrays.asList((Integer []) ary.getArray());
        }
        return null;
    }

    public static final List<Integer> getIntegerList(final ResultSet rs, final int columnIndex) throws SQLException
    {
        final Array ary = rs.getArray(columnIndex);
        if (ary != null) {
            Preconditions.checkArgument(ary.getBaseType() == Types.INTEGER || ary.getBaseType() == Types.SMALLINT);
            return Arrays.asList((Integer []) ary.getArray());
        }
        return null;
    }

    /**
     * Returns an array of Long or null if the input is null. Null is intentional (not empty list), to be
     * able to distinguish between null value and empty list in the db.
     */
    public static final List<Long> getLongList(final ResultSet rs, final String columnName) throws SQLException
    {
        final Array ary = rs.getArray(columnName);
        if (ary != null) {
            Preconditions.checkArgument(ary.getBaseType() == Types.BIGINT);
            return Arrays.asList((Long []) ary.getArray());
        }
        return null;
    }

    public static final List<Long> getLongList(final ResultSet rs, final int columnIndex) throws SQLException
    {
        final Array ary = rs.getArray(columnIndex);
        if (ary != null) {
            Preconditions.checkArgument(ary.getBaseType() == Types.BIGINT);
            return Arrays.asList((Long []) ary.getArray());
        }
        return null;
    }
}
