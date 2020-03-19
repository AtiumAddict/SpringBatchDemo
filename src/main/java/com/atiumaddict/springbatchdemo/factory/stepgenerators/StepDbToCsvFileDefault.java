package com.atiumaddict.springbatchdemo.factory.stepgenerators;

import com.atiumaddict.springbatchdemo.factory.StepGeneratorContext;
import com.atiumaddict.springbatchdemo.factory.listen.StepExecutionListenerDefault;
import com.atiumaddict.springbatchdemo.factory.read.JdbcCursorItemReaderDefault;
import com.atiumaddict.springbatchdemo.factory.write.writers.FlatFileItemWriterCsvDefault;
import org.springframework.batch.core.Step;
import org.springframework.util.Assert;

public class StepDbToCsvFileDefault<S, T> extends StepGenerator {
    public StepDbToCsvFileDefault(StepGeneratorContext stepGeneratorContext) {
        super(stepGeneratorContext);
    }

    @Override
    public Step generateStep() {
        return stepGeneratorContext.getBatchConfiguration().stepBuilderFactory.get(stepGeneratorContext.getStepName())
                .listener(new StepExecutionListenerDefault(stepGeneratorContext.getStepName()))
                .<S, T>chunk(stepGeneratorContext.getChunkSize())
                .reader(new JdbcCursorItemReaderDefault(
                        stepGeneratorContext.getBatchConfiguration().dataSource,
                        stepGeneratorContext.getReaderSqlStatement(),
                        stepGeneratorContext.getSourceClass()))
                .processor(stepGeneratorContext.getProcessor())
                .writer(new FlatFileItemWriterCsvDefault(
                        stepGeneratorContext.getOutputResource(),
                        stepGeneratorContext.getFieldNames()
                ))
                .build();
    }

    @Override
    public void validateContext() {
        Object[] notNullElements = {
                stepGeneratorContext.getReaderSqlStatement(),
                stepGeneratorContext.getSourceClass(),
                stepGeneratorContext.getOutputResource(),
                stepGeneratorContext.getFieldNames()
        };
        Assert.noNullElements(notNullElements, "Step context is missing one or more elements!");
    }
}
