package com.atiumaddict.springbatchdemo;

import org.springframework.jdbc.core.JdbcTemplate;

public class DataHelper {
    public static void deleteTables(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.update("DELETE FROM patients");
        jdbcTemplate.update("DELETE FROM infections");
        jdbcTemplate.update("DELETE FROM doctors");
    }
}
