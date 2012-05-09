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
package com.nesscomputing.jdbi.binding;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;

import org.skife.jdbi.v2.SQLStatement;
import org.skife.jdbi.v2.sqlobject.Binder;
import org.skife.jdbi.v2.sqlobject.BinderFactory;

/**
 * Like {@link BindBeanFactory} except it also defines arguments for use in
 * StringTemplate expressions
 */
@SuppressWarnings("rawtypes")
public class BindDefineBeanFactory implements BinderFactory {
    @Override
    public Binder build(Annotation annotation) {
        return new Binder<BindDefineBean, Object>() {
            @Override
            public void bind(SQLStatement q, BindDefineBean bind, Object arg) {
                final String prefix;
                if ("___jdbi_bare___".equals(bind.value())) {
                    prefix = "";
                }
                else {
                    prefix = bind.value() + ".";
                }

                try {
                    BeanInfo infos = Introspector.getBeanInfo(arg.getClass());
                    PropertyDescriptor[] props = infos.getPropertyDescriptors();
                    for (PropertyDescriptor prop : props) {
                        String key = prefix + prop.getName();
                        Object value = prop.getReadMethod().invoke(arg);
                        q.bind(key, value);
                        q.define(key, value);
                    }
                }
                catch (Exception e) {
                    throw new IllegalStateException("unable to bind bean properties", e);
                }
            }
        };
    }
}
