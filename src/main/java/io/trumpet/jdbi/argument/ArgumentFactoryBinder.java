package io.trumpet.jdbi.argument;

import org.skife.jdbi.v2.tweak.ArgumentFactory;

import com.google.inject.Binder;
import com.google.inject.TypeLiteral;
import com.google.inject.binder.LinkedBindingBuilder;
import com.google.inject.multibindings.Multibinder;

public final class ArgumentFactoryBinder
{
    private ArgumentFactoryBinder()
    {
    }

    public static LinkedBindingBuilder<ArgumentFactory<?>> bindArgumentFactory(final Binder binder)
    {
        final Multibinder<ArgumentFactory<?>> argumentFactoryBinder = Multibinder.newSetBinder(binder, new TypeLiteral<ArgumentFactory<?>>() {});
        return argumentFactoryBinder.addBinding();
    }
}
