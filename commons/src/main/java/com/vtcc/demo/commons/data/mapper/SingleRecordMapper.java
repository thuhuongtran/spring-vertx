package com.vtcc.demo.commons.data.mapper;

import org.jooq.Record;
import org.jooq.RecordMapper;

public class SingleRecordMapper<R extends Record, E> implements RecordMapper<R, E> {
    private Class<E> clazz;
    private R record;

    public SingleRecordMapper(Class<E> clazz) {
        this.clazz = clazz;
    }

    @Override
    public E map(R record) {
        this.record = record;
        return null;
    }
}
