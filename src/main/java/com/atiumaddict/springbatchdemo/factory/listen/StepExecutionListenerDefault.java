package com.atiumaddict.springbatchdemo.factory.listen;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

/**
 * Only logs the fact that a step is complete or has failed.
 */
@Slf4j
public class StepExecutionListenerDefault implements StepExecutionListener {
    private String stepName;

    public StepExecutionListenerDefault(String stepName) {
        this.stepName = stepName;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {

    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        if (stepExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info(stepName + " STEP IS COMPLETE!!!");
            return ExitStatus.COMPLETED;
        } else {
            log.error(stepName + " STEP HAS FAILED!!!");
            throw new RuntimeException(stepExecution.getFailureExceptions().get(0));
//            return ExitStatus.FAILED;
        }
    }
}
