package com.atiumaddict.springbatchdemo.factory.write.writers;

import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

public class JdbcBatchItemWriterDefault extends JdbcBatchItemWriter {
    /**
     * @param dataSource   The datasource we need to access.
     * @param jdbcTemplate A NamedParameteraJdbcTemplate.
     * @param sqlStatement The SQl statement that that updates or inserts.
     */
    public JdbcBatchItemWriterDefault(DataSource dataSource, NamedParameterJdbcTemplate jdbcTemplate, String sqlStatement) {
        setDataSource(dataSource);
        setJdbcTemplate(jdbcTemplate);
        setSql(sqlStatement);
        setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        afterPropertiesSet();
    }
}
