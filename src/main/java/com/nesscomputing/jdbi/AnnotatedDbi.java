package com.nesscomputing.jdbi;

import org.skife.jdbi.v2.IDBI;
import org.skife.jdbi.v2.tweak.StatementLocator;


/**
 * Wraps the DBI so that all all Handles use an AnnotatedStatementLocator. This is used as follows:
 *
 * <pre>
 * &#64;StatementLocation("foo-sql.st");
 * public class FooDAO {
 *
 *   &#64;Inject
 *   public FooDAO(final IDBI dbi) {
 *    this.dbi = new AnnotatedDbi(dbi, this);
 *   }
 *
 *   ...
 * }
 * </pre>
 *
 * @see AnnotatedStatementLocator
 */
public class AnnotatedDbi extends DbiAdapter
{
    public AnnotatedDbi(final IDBI delegate, final Object annotatedClass)
    {
        super(new WrappingDbi.Builder(delegate).setLocator(new AnnotatedStatementLocator(annotatedClass)).build());
    }

    public AnnotatedDbi(final IDBI delegate, final Object annotatedClass, final StatementLocator defaultLocator)
    {
        super(new WrappingDbi.Builder(delegate).setLocator(new AnnotatedStatementLocator(annotatedClass, defaultLocator)).build());
    }
}


