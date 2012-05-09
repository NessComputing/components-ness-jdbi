package io.trumpet.jdbi;

import static java.lang.String.format;

import java.lang.annotation.Annotation;
import java.security.ProviderException;

import org.skife.jdbi.v2.IDBI;

import com.google.inject.Binder;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.Scopes;
import com.google.inject.name.Names;

public final class SqlObjectBinder
{
    private static enum Style
    {
        ON_DEMAND, OPEN
    };

    private SqlObjectBinder()
    {
    }

    public static <T> void bindSqlObjectOnDemand(final Binder binder, final Class<T> sqlObjectClass, final String dbName)
    {
        bindSqlObjectOnDemand(binder, sqlObjectClass, Names.named(dbName));
    }

    public static <T> void bindSqlObjectOnDemand(final Binder binder, final Class<T> sqlObjectClass, final Annotation annotation)
    {
        binder.bind(sqlObjectClass).toProvider(new SqlObjectProvider<T>(sqlObjectClass, annotation, Style.ON_DEMAND)).in(Scopes.SINGLETON);
    }

    public static <T> void bindSqlObjectOpen(final Binder binder, final Class<T> sqlObjectClass, final String dbName)
    {
        bindSqlObjectOnDemand(binder, sqlObjectClass, Names.named(dbName));
    }

    public static <T> void bindSqlObjectOpen(final Binder binder, final Class<T> sqlObjectClass, final Annotation annotation)
    {
        binder.bind(sqlObjectClass).toProvider(new SqlObjectProvider<T>(sqlObjectClass, annotation, Style.OPEN)).in(Scopes.SINGLETON);
    }


    static class SqlObjectProvider<T> implements Provider<T>
    {
        private final Class<? extends T> sqlObjectClass;
        private final Annotation annotation;
        private final Style style;

        private IDBI idbi = null;

        private SqlObjectProvider(final Class<? extends T> sqlObjectClass, final Annotation annotation, final Style style)
        {
            this.sqlObjectClass = sqlObjectClass;
            this.annotation = annotation;
            this.style = style;
        }

        @Inject
        void setInjector(final Injector injector)
        {
            this.idbi = injector.getInstance(Key.get(IDBI.class, annotation));
        }

        @Override
        public T get()
        {
            switch (style) {
                case ON_DEMAND:
                    return idbi.onDemand(sqlObjectClass);
                case OPEN:
                    return idbi.open(sqlObjectClass);
            }
            throw new ProviderException(format("Unknown provider style: %s", style));
        }
    }
}
