package com.atiumaddict.springbatchdemo.factory.listen;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

/**
 * Only logs the fact that a job is complete or has failed.
 */
@Slf4j
public class JobExecutionListenerDefault implements JobExecutionListener {

    private String jobName;

    public JobExecutionListenerDefault(String jobName) {
        this.jobName = jobName;
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info(jobName + " JOB IS COMPLETE!!!");
        } else {
            log.error(jobName + " JOB HAS FAILED!!!");
        }
    }
}
