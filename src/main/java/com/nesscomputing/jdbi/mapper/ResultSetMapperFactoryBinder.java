package com.nesscomputing.jdbi.mapper;

import org.skife.jdbi.v2.ResultSetMapperFactory;

import com.google.inject.Binder;
import com.google.inject.binder.LinkedBindingBuilder;
import com.google.inject.multibindings.Multibinder;

/**
 * Binds a {@link ResultSetMapperFactory} so that JDBI will automatically use it
 */
public class ResultSetMapperFactoryBinder {
    private ResultSetMapperFactoryBinder() { }

    public static LinkedBindingBuilder<ResultSetMapperFactory> bindResultSetMapperFactory(Binder binder) {
        return Multibinder.newSetBinder(binder, ResultSetMapperFactory.class).addBinding();
    }
}
