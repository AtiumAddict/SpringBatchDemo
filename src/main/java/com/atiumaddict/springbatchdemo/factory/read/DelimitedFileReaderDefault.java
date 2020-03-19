package com.atiumaddict.springbatchdemo.factory.read;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;

import java.util.Optional;

import static com.atiumaddict.springbatchdemo.utils.JobUtils.LINES_TO_SKIP_DEFAULT;

/**
 * Default FlatFileItemReader for delimited files.
 */
public class DelimitedFileReaderDefault extends FlatFileItemReader {

    /**
     * Creates a {@link DelimitedFileReaderDefault} according to the resource provided, with a line mapper that uses the
     * delimiting character, the field names and the {@link FieldSetMapper} provided in the {@link FileReaderContext}.
     */
    public DelimitedFileReaderDefault(FileReaderContext context) {
        setResource(context.getDefaultFileResource());
        setLinesToSkip(Optional.ofNullable(context.getLinesToSkip()).orElse(LINES_TO_SKIP_DEFAULT));
        setLineMapper(getLineMapper(context.getDelimiter(), context.getFieldNames(), context.getFieldSetMapper()));
    }

    public DefaultLineMapper getLineMapper(String delimiter, String[] fieldNames, FieldSetMapper fieldSetMapper) {
        DefaultLineMapper lineMapper = new DefaultLineMapper();
        lineMapper.setLineTokenizer(getLineTokenizer(delimiter, fieldNames));
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }

    public LineTokenizer getLineTokenizer(String delimiter, String[] fieldNames) {
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        lineTokenizer.setDelimiter(delimiter);
        lineTokenizer.setNames(fieldNames);

        return lineTokenizer;
    }
}
