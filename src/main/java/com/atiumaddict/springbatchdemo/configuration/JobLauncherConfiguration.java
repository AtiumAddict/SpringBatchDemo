package com.atiumaddict.springbatchdemo.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
@Slf4j
public class JobLauncherConfiguration {

    @Autowired
    public JobRepository simpleJobRepository;

    public JobRepository asyncJobRepository;

    @Autowired
    PlatformTransactionManager transactionManager;

    @Autowired
    public JobExplorer jobExplorer;

    public JdbcTemplate jdbcTemplate;

    public DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.dataSource = dataSource;
    }

    @Autowired
    public void setAsyncJobRepository(DataSource dataSource) {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setValidateTransactionState(false);
        factory.setDataSource(dataSource);
        factory.setTransactionManager(transactionManager);
        try {
            factory.afterPropertiesSet();
            this.asyncJobRepository = factory.getObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Bean
    public JobLauncher asyncJobLauncher() {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(asyncJobRepository);
        jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
        return jobLauncher;
    }

    @Bean
    public JobLauncher simpleJobLauncher() {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(simpleJobRepository);
        return jobLauncher;
    }
}
