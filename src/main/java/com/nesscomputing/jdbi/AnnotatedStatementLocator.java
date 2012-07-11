/**
 * Copyright (C) 2012 Ness Computing, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.nesscomputing.jdbi;

import com.nesscomputing.jdbi.template.TemplateGroupLoader;
import com.nesscomputing.logging.Log;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.StatementLocator;

/**
 * Locates the SQL file used to load the statements via a class specific annotation. A statement must
 * start with a marker string and is loaded from a locator based off the handle. Statement names of the
 * form <class name>:<statement name> are looked up dynamically based on the annotation on <class name>
 * instead of the default class name given at construction time.
 */
public class AnnotatedStatementLocator implements StatementLocator
{
    public static final String STATEMENT_CLASS = "_jmx_class";
	public static final String STATEMENT_GROUP = "_jmx_group";
	public static final String STATEMENT_NAME = "_jmx_name";

    private static final Log LOG = Log.findLog();

    private final String markerString;

    private final StatementLocator defaultLocator;

    private final SqlLocator defaultSqlLocator;

    private final String defaultClassName;

    public AnnotatedStatementLocator(final Object annotatedObject)
    {
        this(annotatedObject.getClass(), null);
    }

    public AnnotatedStatementLocator(final Object annotatedObject, final StatementLocator defaultLocator)
    {
        this(annotatedObject.getClass(), defaultLocator, "@");
    }

    public AnnotatedStatementLocator(final Object annotatedObject, final StatementLocator defaultLocator, final String markerString)
    {
        this(annotatedObject.getClass(), defaultLocator, markerString);
    }

    public AnnotatedStatementLocator(final Class<?> annotatedClass)
    {
        this(annotatedClass, null);
    }

    public AnnotatedStatementLocator(final Class<?> annotatedClass, final StatementLocator defaultLocator)
    {
        this(annotatedClass, defaultLocator, "@");
    }

    public AnnotatedStatementLocator(final Class<?> annotatedClass, final StatementLocator defaultLocator, final String markerString)
    {
        this.defaultLocator = defaultLocator;
        this.markerString = markerString;

        this.defaultClassName = annotatedClass.getName();

        this.defaultSqlLocator = new SqlLocator(annotatedClass);
    }

    @Override
    public String locate(final String statementName, final StatementContext context) throws Exception
    {
        if (StringUtils.isEmpty(statementName)) {
            throw new IllegalStateException("Statement Name can not be empty/null!");
        }

        if (statementName.startsWith(markerString)) {
            return defaultSqlLocator.getTemplate(statementName.substring(markerString.length()), context);
        }

        final String [] fieldElements = StringUtils.split(statementName, ':');
        if (fieldElements.length != 2 || !fieldElements[1].startsWith(markerString)) {
            return defaultLocate(statementName, context);
        }

        // If the class in the tuple is actually the class for which this locator is intended, don't load
        // everything again, just reuse the existing sqllocator.
        if (defaultClassName.equals(fieldElements[0])) {
            return defaultSqlLocator.getTemplate(fieldElements[1].substring(markerString.length()), context);
        }

        try {
            final SqlLocator sqlLocator = new SqlLocator(Class.forName(fieldElements[0]));
            return sqlLocator.getTemplate(fieldElements[1].substring(markerString.length()), context);
        }
        catch (ClassNotFoundException cnfe) {
            LOG.trace(cnfe, "Ignoring, falling back to default locator");
        }
        catch (IllegalArgumentException ex) {
            LOG.trace(ex, "Ignoring, falling back to default locator");
        }

        return defaultLocate(statementName, context);
    }

    protected String defaultLocate(String pathOrSql, StatementContext context) throws Exception
    {
        if (defaultLocator == null) {
            return pathOrSql;
        }
        else {
            return defaultLocator.locate(pathOrSql, context);
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this)
                .append(defaultSqlLocator.groupName)
                .append(markerString)
                .append(defaultLocator)
                .toString();
    }

    private final class SqlLocator
    {
        private final StringTemplateGroup templateGroup;
        private final String groupName;
        private final String className;

        private SqlLocator(final Class<?> clazz)
        {
            this.className = clazz.getName();

            final StatementLocation location = clazz.getAnnotation(StatementLocation.class);

            if (location == null) {
                throw new IllegalArgumentException(clazz.getName() + " is missing the @StatementLocator annotation!");
            }

            final String sqlFile = "/" + (StringUtils.isNotEmpty(location.value()) ? location.value() : "sql.st");
            final String sqlPackage = clazz.getPackage().getName();
            this.groupName = sqlPackage + sqlFile;

            this.templateGroup = TemplateGroupLoader.load(sqlPackage, sqlFile);
            LOG.trace("Loading templates from %s!", groupName);
        }

        public String getTemplate(final String statementName, final StatementContext context) throws Exception
        {
            StringTemplate template;

            try {
                template = templateGroup.getInstanceOf(statementName);
            }
            catch (IllegalArgumentException ex) {
                LOG.info("Didn't find a template when expected: %s: %s", groupName, statementName);
                return defaultLocate(statementName, context);
            }

            // This can be used by timing collectors to figure out which statement was used.
            context.setAttribute(STATEMENT_CLASS, className);
            context.setAttribute(STATEMENT_GROUP, groupName);
            context.setAttribute(STATEMENT_NAME, statementName);

            template.setAttributes(context.getAttributes());

            final String sql = template.toString();

            LOG.trace("SQL for %s: %s", groupName, sql);

            return sql;
        }
    }
}
