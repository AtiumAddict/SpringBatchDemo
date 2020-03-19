package com.atiumaddict.springbatchdemo.factory;

import com.atiumaddict.springbatchdemo.configurations.BatchConfiguration;
import com.atiumaddict.springbatchdemo.factory.read.FileReaderContext;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.core.io.Resource;

import javax.validation.constraints.NotNull;

/**
 * Context needed for the generation of a step.
 */
@Data
@AllArgsConstructor
@Builder
public class StepGeneratorContext {
    @NotNull
    private BatchConfiguration batchConfiguration;
    @NotNull
    private String stepName;
    private FileReaderContext readerContext;
    private String readerSqlStatement;
    private Class sourceClass;
    private Class targetClass;
    private ItemProcessor processor;
    private String writerSqlStatement;
    private Integer chunkSize;
    private Resource outputResource;
    private String format;
    private String[] fieldNames;
    private ItemReader reader;
    private ItemWriter writer;
    private Integer startLimit;
}
