package io.trumpet.jdbi.mapper;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

/**
 * A result set mapper that allows efficient retrieval of fields using a prefix (i.e. if multiple elements
 * are returned from a query.
 */
public abstract class PrefixResultSetMapper<T> implements ResultSetMapper<T>
{
    private final String prefix;

    private final Map<String, String> fieldNameCache = new ConcurrentHashMap<String, String>();

    protected PrefixResultSetMapper(final String prefix)
    {
        this.prefix = StringUtils.isBlank(prefix) ? null : prefix;
    }

    protected String buildName(final String fieldName)
    {
        if (prefix == null) {
            return fieldName;
        }

        if (fieldNameCache.containsKey(fieldName)) {
            return fieldNameCache.get(fieldName);
        }
        else {
            final String result = prefix + fieldName;
            fieldNameCache.put(fieldName, result);
            return result;
        }
    }
}
