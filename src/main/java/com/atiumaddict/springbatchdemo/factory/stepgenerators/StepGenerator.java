package com.atiumaddict.springbatchdemo.factory.stepgenerators;

import com.atiumaddict.springbatchdemo.factory.StepFactory;
import com.atiumaddict.springbatchdemo.factory.StepGeneratorContext;
import org.springframework.batch.core.Step;

public abstract class StepGenerator {

    StepGeneratorContext stepGeneratorContext;

    /**
     * Used by {@link StepFactory} to generate steps, given a context.
     */
    public StepGenerator(StepGeneratorContext stepGeneratorContext) {
        this.stepGeneratorContext = stepGeneratorContext;
        validateContext();
    }

    /**
     * Builds the step through the StepBuilderFactory from the batchConficuration of the stepGeneratorContext, adding
     * different elements for different step types.
     */
    public abstract Step generateStep();

    /**
     * Checks that certain attributes of the stepGeneratorContext are not null, depending on the step type.
     */
    public abstract void validateContext();
}
