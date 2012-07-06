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

import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.IDBI;
import org.skife.jdbi.v2.tweak.StatementBuilder;
import org.skife.jdbi.v2.tweak.StatementLocator;
import org.skife.jdbi.v2.tweak.StatementRewriter;

/**
 * Wraps a DBI so that the statement locator, builder, or rewriter can be set without affecting the delegate DBI.
 * @deprecated this class cannot really be made to work if the DBI interface is ever extended
 */
@Deprecated
public class WrappingDbi extends DbiAdapter implements IDBI
{
    private final StatementLocator locator;
    private final StatementBuilder builder;
    private final StatementRewriter rewriter;

    private WrappingDbi(final IDBI delegate,
    				   final StatementLocator locator,
    				   final StatementBuilder builder,
    				   final StatementRewriter rewriter)
    {
        super(delegate, true);
        this.locator = locator;
        this.builder = builder;
        this.rewriter = rewriter;
    }

    @Override
    protected Handle wrapHandle(final Handle handle)
    {
        if (locator != null) {
            handle.setStatementLocator(locator);
        }

        if (builder != null) {
            handle.setStatementBuilder(builder);
        }

        if (rewriter != null) {
            handle.setStatementRewriter(rewriter);
        }

        return handle;
    }

    public static final class Builder
    {
    	private final IDBI delegate;
        private StatementLocator locator = null;
        private StatementBuilder builder = null;
        private StatementRewriter rewriter = null;

        public Builder(final IDBI delegate)
        {
        	this.delegate = delegate;
        }

        public IDBI build()
        {
        	return new WrappingDbi(delegate, locator, builder, rewriter);
        }

		public Builder setLocator(final StatementLocator locator)
		{
			this.locator = locator;
			return this;
		}

		public Builder setBuilder(final StatementBuilder builder)
		{
			this.builder = builder;
			return this;
		}

		public Builder setRewriter(final StatementRewriter rewriter)
		{
			this.rewriter = rewriter;
			return this;
		}
    }
}
