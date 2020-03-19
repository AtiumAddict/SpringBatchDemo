package com.atiumaddict.springbatchdemo.factory.stepgenerators;

import com.atiumaddict.springbatchdemo.factory.StepFactory;

/**
 * The different types of steps, which are passed as a parameter to the {@link StepFactory},
 * in order to generate the appropriate step.
 */
public enum StepType {
    DB_TO_DB,
    DB_TO_CSV_FILE,
    DB_TO_FIXED_LENGTH_FILE,
    FIXED_LENGTH_FILE_TO_DB,
    DELIMTED_FILE_TO_DB
}
