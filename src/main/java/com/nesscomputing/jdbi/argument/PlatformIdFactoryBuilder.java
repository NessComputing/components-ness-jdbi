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
package com.nesscomputing.jdbi.argument;


import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.Argument;
import org.skife.jdbi.v2.tweak.ArgumentFactory;

import com.nesscomputing.types.PlatformId;

/**
 * Allows registration of ArgumentFactories to accept platformIds as JDBI arguments.
 */

public class PlatformIdFactoryBuilder
{
    public static <T> ArgumentFactory<PlatformId<T>> build(final Class<T> clazz)
    {
        return new ArgumentFactory<PlatformId<T>>() {

            @Override
            public boolean accepts(final Class<?> expectedType, final Object value, final StatementContext ctx)
            {
                return (clazz.isInstance(value));
            }

            @Override
            public Argument build(final Class<?> expectedType, final PlatformId<T> value, final StatementContext ctx)
            {
                return PlatformIdArgument.from(value);
            }
        };
    }
}
