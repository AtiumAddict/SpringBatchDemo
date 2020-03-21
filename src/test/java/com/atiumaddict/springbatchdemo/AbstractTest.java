package com.atiumaddict.springbatchdemo;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "test")
@SpringBootTest
@ContextConfiguration(classes = SpringBatchDemoApplication.class)
public abstract class AbstractTest {
    
    @Autowired
    protected JobLauncherTestUtils jobLauncherTestUtils;

    private static boolean dataInitializationIsDone = false;

    protected JdbcTemplate jdbcTemplate;

    protected DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.dataSource = dataSource;
    }

    @Before
    public void prepareData() {
        DataHelper.deleteTables(jdbcTemplate);
    }


    @SpringBootApplication
    static class TestConfiguration {

        @Bean
        public FlywayMigrationStrategy dropCreate() {
            return flyway -> {
                flyway.clean();
                flyway.migrate();
            };
        }
    }
}
