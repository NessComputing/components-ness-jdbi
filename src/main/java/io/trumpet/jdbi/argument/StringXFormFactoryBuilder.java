package io.trumpet.jdbi.argument;



import com.google.common.base.Function;
import com.google.common.base.Functions;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.Argument;
import org.skife.jdbi.v2.tweak.ArgumentFactory;

import javax.annotation.concurrent.Immutable;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

@Immutable
@Deprecated
public class StringXFormFactoryBuilder {

    public static <T> ArgumentFactory<T> build(Class<T> clazz) {
        return build(clazz, Functions.toStringFunction());
    }

    public static <T> ArgumentFactory<T> build(final Class<T> clazz, final Function<? super T, String> transform) {
        return new ArgumentFactory<T>() {
            @Override
            public boolean accepts(Class<?> expectedType, Object value, StatementContext ctx) {
                return (clazz.isInstance(value));
            }

            @Override
            public Argument build(Class<?> expectedType, final T value, StatementContext ctx) {
                return new Argument() {
                    @Override
                    public void apply(int position, PreparedStatement statement, StatementContext ctx) throws SQLException {
                        if (value == null) {
                            statement.setNull(position, Types.NULL);
                        } else {
                            statement.setString(position, transform.apply(value));
                        }
                    }
                };
            }
        };
    }
}
