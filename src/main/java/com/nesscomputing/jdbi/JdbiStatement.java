package com.nesscomputing.jdbi;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.skife.jdbi.v2.Call;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.PreparedBatch;
import org.skife.jdbi.v2.Query;
import org.skife.jdbi.v2.Script;
import org.skife.jdbi.v2.Update;

/**
 * Allows simple declarations of SQL statements in a template as constants:
 *
 * <pre>
 *   public class FooDAO {
 *     public static final Package pkg = FooDAO.class.getPackage();
 *     public static final JdbiStatement UPDATE = new JdbiStatement(pkg, &quot;update&quot;); // loads from sql.st, template 'update()'
 *   ...
 *     return db.withHandle(new HandleCallback&lt;Integer&gt;() {
 *       public Integer withHandle(final Handle handle) throws Exception {
 *         return UPDATE.createStatement(handle).bind(&quot;foo&quot;, ...).execute();
 *       }
 *     }
 *   }
 * </pre>
 */
public final class JdbiStatement
{
    private final String queryName;

    public JdbiStatement(final String queryName)
    {
        this.queryName = queryName;
    }

    public JdbiStatement(final Class<?> clazz, final String queryName)
    {
        this(clazz.getName() + ":" + queryName);
    }

    public JdbiStatement(final Package pkg, final String queryName)
    {
        this(pkg.getName() + ":" + queryName);
    }

    /**
     * @see Handle#createQuery(String)
     */
    public Query<Map<String, Object>> createQuery(final Handle handle)
    {
        return handle.createQuery(queryName);
    }

    /**
     * @see Handle#createStatement(String)
     */
    public Update createStatement(final Handle handle)
    {
        return handle.createStatement(queryName);
    }

    /**
     * @see Handle#createScript(String)
     */
    public Script createScript(final Handle handle)
    {
        return handle.createScript(queryName);
    }

    /**
     * @see Handle#createCall
     */
    public Call createCall(final Handle handle)
    {
        return handle.createCall(queryName);
    }

    /**
     * @see Handle#prepareBatch(String)
     */
    public PreparedBatch createPreparedBatch(final Handle handle)
    {
        return handle.prepareBatch(queryName);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this)
            .append("queryName", queryName)
            .toString();
    }
}
