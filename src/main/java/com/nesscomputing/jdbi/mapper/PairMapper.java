package com.nesscomputing.jdbi.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.tuple.Pair;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class PairMapper<U, V> implements ResultSetMapper<Pair<U, V>>
{
    private final ResultSetMapper<U> leftMapper;
    private final ResultSetMapper<V> rightMapper;

    public PairMapper(final ResultSetMapper<U> leftMapper, final ResultSetMapper<V> rightMapper)
    {
        this.leftMapper = leftMapper;
        this.rightMapper = rightMapper;
    }

    @Override
    public Pair<U, V> map(int index, final ResultSet r, final StatementContext ctx) throws SQLException
    {
        final U leftValue = leftMapper.map(index, r, ctx);
        final V rightValue = rightMapper.map(index, r, ctx);

        return Pair.of(leftValue, rightValue);
    }
}
