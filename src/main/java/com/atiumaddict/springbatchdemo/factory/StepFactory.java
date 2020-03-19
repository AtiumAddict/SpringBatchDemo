package com.atiumaddict.springbatchdemo.factory;

import com.atiumaddict.springbatchdemo.factory.stepgenerators.*;
import org.springframework.batch.core.Step;

public class StepFactory<S, T> {
    private StepGeneratorContext stepGeneratorContext;

    public StepFactory(StepGeneratorContext stepGeneratorContext) {
        this.stepGeneratorContext = stepGeneratorContext;
    }

    /**
     * Creates a step whose type corresponds to the stepType provided.
     */
    public Step createStep(StepType stepType) {
        switch (stepType) {
            case DB_TO_DB:
                return new StepDbToDbDefault<S, T>(stepGeneratorContext).generateStep();
            case DB_TO_CSV_FILE:
                return new StepDbToCsvFileDefault<S, T>(stepGeneratorContext).generateStep();
            case DB_TO_FIXED_LENGTH_FILE:
                return new StepDbToFixedLengthFileDefault<S, T>(stepGeneratorContext).generateStep();
            case FIXED_LENGTH_FILE_TO_DB:
                return new StepFixedLengthFileToDbDefault<S, T>(stepGeneratorContext).generateStep();
            case DELIMTED_FILE_TO_DB:
                return new StepDelimitedFileToDbDefault<S, T>(stepGeneratorContext).generateStep();
            default:
                return null;
        }
    }
}
