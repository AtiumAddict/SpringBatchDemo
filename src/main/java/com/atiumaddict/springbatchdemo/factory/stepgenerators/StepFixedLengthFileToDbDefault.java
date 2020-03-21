package com.atiumaddict.springbatchdemo.factory.stepgenerators;

import com.atiumaddict.springbatchdemo.factory.StepGeneratorContext;
import com.atiumaddict.springbatchdemo.factory.listen.StepExecutionListenerDefault;
import com.atiumaddict.springbatchdemo.factory.read.FlatFileItemReaderFixedLengthDefault;
import com.atiumaddict.springbatchdemo.factory.write.writers.JdbcBatchItemWriterDefault;
import org.springframework.batch.core.Step;
import org.springframework.util.Assert;

import java.util.Optional;

public class StepFixedLengthFileToDbDefault<S, T> extends StepGenerator {
    public StepFixedLengthFileToDbDefault(StepGeneratorContext stepGeneratorContext) {
        super(stepGeneratorContext);
    }

    @Override
    public Step generateStep() {
        return stepGeneratorContext.getBatchConfiguration().stepBuilderFactory.get(stepGeneratorContext.getStepName())
                .listener(new StepExecutionListenerDefault(stepGeneratorContext.getStepName()))
                .<S, T>chunk(Optional.ofNullable(stepGeneratorContext.getChunkSize()).orElse(100))
                .reader(new FlatFileItemReaderFixedLengthDefault(stepGeneratorContext.getReaderContext()))
                .processor(stepGeneratorContext.getProcessor())
                .writer(Optional.ofNullable(stepGeneratorContext.getWriter())
                        .orElse(new JdbcBatchItemWriterDefault(
                                stepGeneratorContext.getBatchConfiguration().dataSource,
                                stepGeneratorContext.getBatchConfiguration().namedParameterJdbcTemplate,
                                stepGeneratorContext.getWriterSqlStatement())))
                .build();
    }

    @Override
    public void validateContext() {
        Object[] notNullElements = {
                stepGeneratorContext.getReaderContext(),
                stepGeneratorContext.getReaderContext().getDefaultFileResource(),
                stepGeneratorContext.getReaderContext().getRanges(),
                Optional.ofNullable((Object) stepGeneratorContext.getWriter()).orElse(stepGeneratorContext.getWriterSqlStatement())
        };
        Assert.noNullElements(notNullElements, "Step context is missing one or more elements!");
    }
}
