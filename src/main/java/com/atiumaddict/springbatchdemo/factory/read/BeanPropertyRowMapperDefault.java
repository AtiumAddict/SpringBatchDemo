package com.atiumaddict.springbatchdemo.factory.read;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

/**
 * A BeanPropertyRowMapper that sets default values for primitive type attributes when null values are found.
 */
public class BeanPropertyRowMapperDefault<T> extends BeanPropertyRowMapper<T> {
    public BeanPropertyRowMapperDefault(Class<T> mappedClass) {
        super(mappedClass);
        this.setPrimitivesDefaultedForNullValue(true);
    }
}
