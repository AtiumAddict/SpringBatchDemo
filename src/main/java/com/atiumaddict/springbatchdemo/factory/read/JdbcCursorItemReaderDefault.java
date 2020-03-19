package com.atiumaddict.springbatchdemo.factory.read;

import org.springframework.batch.item.database.JdbcCursorItemReader;

import javax.sql.DataSource;

/**
 * Default dbReader that uses the default BeanPropertyRowMapper.
 */
public class JdbcCursorItemReaderDefault extends JdbcCursorItemReader {
    /**
     * @param dataSource   The datasource we need to access.
     * @param sqlStatement The SQL Statement that selects the information we need to get in the DTO.
     * @param clazz        The type of the DTO.
     */
    public JdbcCursorItemReaderDefault(DataSource dataSource, String sqlStatement, Class clazz) {
        setDataSource(dataSource);
        setSql(sqlStatement);
        setRowMapper(new BeanPropertyRowMapperDefault(clazz));
    }
}
