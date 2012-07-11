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

import javax.annotation.Nullable;

import org.skife.jdbi.v2.DBI;

import com.google.common.base.Function;

/**
 * Customizes the DBI so that all all statements use an AnnotatedStatementLocator. This is used as follows:
 *
 * <pre>
 * public class FooDAOModule extends AbstractModule {
 *   ...
 *      DatabaseModule db = new DatabaseModule();
 *      install (db);
 *      db.bindDbiCustomizer(binder()).toInstance(new AnnotatedDbiCustomizer(FooDAO.class));
 *   ...
 *
 * &#64;StatementLocation("foo-sql.st");
 * public class FooDAO {
 *
 *   &#64;Inject
 *   public FooDAO(final IDBI dbi) {
 *    ...
 *   }
 *
 *   ...
 * }
 * </pre>
 *
 * @see AnnotatedStatementLocator
 */
public class AnnotatedDbiCustomizer implements Function<DBI, DBI>
{

    private final Class<?> annotatedClass;

    public AnnotatedDbiCustomizer(Class<?> annotatedClass)
    {
        this.annotatedClass = annotatedClass;
    }

    @Override
    @Nullable
    public DBI apply(@Nullable DBI input)
    {
        if (input == null)
        {
            return null;
        }

        input.setStatementLocator(new AnnotatedStatementLocator(annotatedClass, input.getStatementLocator()));
        return input;
    }
}
