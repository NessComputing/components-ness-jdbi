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
package com.nesscomputing.jdbi.metrics;

import java.util.concurrent.TimeUnit;

import org.skife.jdbi.v2.TimingCollector;

import com.google.common.base.Objects;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Scopes;
import com.google.inject.name.Named;
import com.yammer.metrics.core.MetricsRegistry;
import com.yammer.metrics.jdbi.InstrumentedTimingCollector;
import com.yammer.metrics.jdbi.strategies.ShortNameStrategy;
import com.yammer.metrics.jdbi.strategies.StatementNameStrategy;

public class DatabaseMetricsModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        bind(StatementNameStrategy.class).toInstance(new ShortNameStrategy("ness.jdbi.metrics"));
        bind(TimingCollector.class).toProvider(TimingCollectorProvider.class).in(Scopes.SINGLETON);
    }

    static class TimingCollectorProvider implements Provider<TimingCollector>
    {
        private final MetricsRegistry metricsRegistry;
        private final StatementNameStrategy statementNameStrategy;

        private TimeUnit durationUnit = TimeUnit.MILLISECONDS;
        private TimeUnit rateUnit = TimeUnit.SECONDS;

        @Inject
        public TimingCollectorProvider(final MetricsRegistry metricsRegistry,
                                       final StatementNameStrategy statementNameStrategy)
        {
            this.metricsRegistry = metricsRegistry;
            this.statementNameStrategy = statementNameStrategy;
        }

        @Inject(optional=true)
        void setDurationUnit(@Named("MetricsTimingCollector.durationUnit") final TimeUnit durationUnit)
        {
            this.durationUnit = Objects.firstNonNull(durationUnit, this.durationUnit);
        }

        @Inject(optional=true)
        void setRateUnit(@Named("MetricsTimingCollector.rateUnit") final TimeUnit rateUnit)
        {
            this.rateUnit = Objects.firstNonNull(rateUnit, this.rateUnit);
        }

        @Override
        public TimingCollector get()
        {
            return new InstrumentedTimingCollector(metricsRegistry,
                                                   statementNameStrategy,
                                                   durationUnit,
                                                   rateUnit);
        }
    }
}
