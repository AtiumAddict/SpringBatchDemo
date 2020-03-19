package com.atiumaddict.springbatchdemo.factory.read;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.core.io.Resource;

import javax.validation.constraints.NotNull;

/**
 * Context needed for the generation of a FlatFileReader
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileReaderContext {
    @NotNull
    private String parameterFilePath;
    @NotNull
    private Resource defaultFileResource;
    @NotNull
    private Class sourceClass;
    @NotNull
    private FieldSetMapper fieldSetMapper;
    @NotNull
    private Range[] ranges;
    @NotNull
    private String[] fieldNames;
    private Integer linesToSkip;
    private String encoding;
    private String delimiter;
}
