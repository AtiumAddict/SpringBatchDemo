package com.atiumaddict.springbatchdemo.factory.stepgenerators;

import com.atiumaddict.springbatchdemo.factory.StepGeneratorContext;
import com.atiumaddict.springbatchdemo.factory.listen.StepExecutionListenerDefault;
import com.atiumaddict.springbatchdemo.factory.read.DelimitedFileReaderDefault;
import com.atiumaddict.springbatchdemo.factory.write.writers.JdbcBatchItemWriterDefault;
import org.springframework.batch.core.Step;
import org.springframework.util.Assert;

import java.util.Optional;

public class StepDelimitedFileToDbDefault<S, T> extends StepGenerator {
    public StepDelimitedFileToDbDefault(StepGeneratorContext stepGeneratorContext) {
        super(stepGeneratorContext);
    }

    @Override
    public Step generateStep() {
        return stepGeneratorContext.getBatchConfiguration().stepBuilderFactory.get(stepGeneratorContext.getStepName())
                .listener(new StepExecutionListenerDefault(stepGeneratorContext.getStepName()))
                .<S, T>chunk(Optional.ofNullable(stepGeneratorContext.getChunkSize()).orElse(1000))
                .reader(new DelimitedFileReaderDefault(stepGeneratorContext.getReaderContext()))
                .processor(stepGeneratorContext.getProcessor())
                .writer(new JdbcBatchItemWriterDefault(
                        stepGeneratorContext.getBatchConfiguration().dataSource,
                        stepGeneratorContext.getBatchConfiguration().namedParameterJdbcTemplate,
                        stepGeneratorContext.getWriterSqlStatement()))
                .build();
    }

    @Override
    public void validateContext() {
        Object[] notNullElements = {
                stepGeneratorContext.getReaderContext(),
                stepGeneratorContext.getSourceClass(),
                stepGeneratorContext.getBatchConfiguration(),
                stepGeneratorContext.getWriterSqlStatement()
        };
        Assert.noNullElements(notNullElements, "Step context is missing one or more elements!");
    }
}
