package com.atiumaddict.springbatchdemo.factory.stepgenerators;

import com.atiumaddict.springbatchdemo.factory.StepGeneratorContext;
import com.atiumaddict.springbatchdemo.factory.listen.StepExecutionListenerDefault;
import com.atiumaddict.springbatchdemo.factory.read.JdbcCursorItemReaderDefault;
import com.atiumaddict.springbatchdemo.factory.write.writers.FlatFileItemWriterFixedLengthDefault;
import org.springframework.batch.core.Step;
import org.springframework.util.Assert;

import java.util.Optional;

public class StepDbToFixedLengthFileDefault<S, T> extends StepGenerator {
    public StepDbToFixedLengthFileDefault(StepGeneratorContext stepGeneratorContext) {
        super(stepGeneratorContext);
    }

    @Override
    public Step generateStep() {
        return stepGeneratorContext.getBatchConfiguration().stepBuilderFactory.get(stepGeneratorContext.getStepName())
                .listener(new StepExecutionListenerDefault(stepGeneratorContext.getStepName()))
                .startLimit(Optional.ofNullable(stepGeneratorContext.getStartLimit()).orElse(2147483647))
                .chunk(stepGeneratorContext.getChunkSize())
                .reader(new JdbcCursorItemReaderDefault(
                        stepGeneratorContext.getBatchConfiguration().dataSource,
                        stepGeneratorContext.getReaderSqlStatement(),
                        stepGeneratorContext.getSourceClass()))
                .processor(stepGeneratorContext.getProcessor())
                .writer(new FlatFileItemWriterFixedLengthDefault(
                        stepGeneratorContext.getOutputResource(),
                        stepGeneratorContext.getFormat(),
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
                stepGeneratorContext.getFormat(),
                stepGeneratorContext.getFieldNames()
        };
        Assert.noNullElements(notNullElements, "Step context is missing one or more elements!");
    }
}
