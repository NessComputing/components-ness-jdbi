package com.nesscomputing.jdbi.metrics;

import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

import org.junit.Assert;
import org.junit.Test;
import org.skife.jdbi.v2.TimingCollector;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Stage;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import com.nesscomputing.jdbi.metrics.DatabaseMetricsModule;
import com.yammer.metrics.guice.InstrumentationModule;

public class TestDatabaseMetricsModule
{
    @Inject
    private TimingCollector tc = null;

    @Inject(optional=true)
    @Nullable
    @Named("MetricsTimingCollector.durationUnit")
    private TimeUnit durationUnit;

    @Inject(optional=true)
    @Nullable
    @Named("MetricsTimingCollector.rateUnit")
    private TimeUnit rateUnit;

    @Test
    public void testSimple()
    {
        final Injector inj = Guice.createInjector(Stage.PRODUCTION,
                                                  new Module() {
                                                      @Override
                                                      public void configure(final Binder binder) {
                                                          binder.requireExplicitBindings();
                                                          binder.disableCircularProxies();
                                                      }
                                                  },
                                                  new DatabaseMetricsModule(),
                                                  new InstrumentationModule());

        inj.injectMembers(this);

        Assert.assertNotNull(tc);
        Assert.assertNull(durationUnit);
        Assert.assertNull(rateUnit);
    }

    @Test
    public void testOne()
    {
        final Injector inj = Guice.createInjector(Stage.PRODUCTION,
                                                  new Module() {
                                                      @Override
                                                      public void configure(final Binder binder) {
                                                          binder.requireExplicitBindings();
                                                          binder.disableCircularProxies();
                                                          binder.bindConstant().annotatedWith(Names.named("MetricsTimingCollector.rateUnit")).to(TimeUnit.MINUTES);
                                                      }
                                                  },
                                                  new DatabaseMetricsModule(),
                                                  new InstrumentationModule());

        inj.injectMembers(this);

        Assert.assertNotNull(tc);
        Assert.assertNull(durationUnit);
        Assert.assertEquals(TimeUnit.MINUTES, rateUnit);
    }

    @Test
    public void testBoth()
    {
        final Injector inj = Guice.createInjector(Stage.PRODUCTION,
                                                  new Module() {
                                                      @Override
                                                      public void configure(final Binder binder) {
                                                          binder.requireExplicitBindings();
                                                          binder.disableCircularProxies();
                                                          binder.bindConstant().annotatedWith(Names.named("MetricsTimingCollector.rateUnit")).to(TimeUnit.MINUTES);
                                                          binder.bindConstant().annotatedWith(Names.named("MetricsTimingCollector.durationUnit")).to(TimeUnit.HOURS);
                                                      }
                                                  },
                                                  new DatabaseMetricsModule(),
                                                  new InstrumentationModule());

        inj.injectMembers(this);

        Assert.assertNotNull(tc);
        Assert.assertEquals(TimeUnit.HOURS, durationUnit);
        Assert.assertEquals(TimeUnit.MINUTES, rateUnit);
    }
}
