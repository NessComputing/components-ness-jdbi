package io.trumpet.jdbi.argument;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;

public class ArgumentFactoryModule implements Module
{
    @Override
    public void configure(final Binder binder)
    {
        ArgumentFactoryBinder.bindArgumentFactory(binder).to(ArgumentFactories.UriArgumentFactory.class).in(Scopes.SINGLETON);
        ArgumentFactoryBinder.bindArgumentFactory(binder).to(ArgumentFactories.DateTimeArgumentFactory.class).in(Scopes.SINGLETON);
        ArgumentFactoryBinder.bindArgumentFactory(binder).to(ArgumentFactories.SqlArrayArgumentFactory.class).in(Scopes.SINGLETON);
        ArgumentFactoryBinder.bindArgumentFactory(binder).to(ArgumentFactories.ArgumentArgumentFactory.class).in(Scopes.SINGLETON);
    }
}
