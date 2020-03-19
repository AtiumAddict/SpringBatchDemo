package com.atiumaddict.springbatchdemo.factory.read;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.core.io.ClassPathResource;

import java.util.Optional;

import static com.atiumaddict.springbatchdemo.utils.JobConstants.READER_ENCODING_DEFAULT;
import static com.atiumaddict.springbatchdemo.utils.JobUtils.LINES_TO_SKIP_DEFAULT;

/**
 * Default FlatFileReader for fixed length files.
 */
public class FlatFileItemReaderFixedLengthDefault extends FlatFileItemReader {

    /**
     * Builds a {@link FlatFileItemReader} for a fixed length file, given the file path and other useful information.
     *
     * @param context The context with all the information needed for the creation of the reader.
     */
    public FlatFileItemReaderFixedLengthDefault(FileReaderContext context) {
        // If a job parameter is not provided, the default file resource is used (production).
        if (context.getParameterFilePath() == null) {
            setResource(context.getDefaultFileResource());
        }
        // If a file path job parameter is provided, it is used to create a resource inside the project (tests).
        else {
            setResource(new ClassPathResource(context.getParameterFilePath()));
        }
        setLinesToSkip(Optional.ofNullable(context.getLinesToSkip()).orElse(LINES_TO_SKIP_DEFAULT));
        setEncoding(Optional.ofNullable(context.getEncoding()).orElse(READER_ENCODING_DEFAULT));
        FixedLengthTokenizer tokenizer = new FixedLengthTokenizer();
        tokenizer.setColumns(context.getRanges());
        tokenizer.setNames(context.getFieldNames());
        DefaultLineMapper mapper = new DefaultLineMapper();
        mapper.setLineTokenizer(tokenizer);
        mapper.setFieldSetMapper(context.getFieldSetMapper());
        setLineMapper(mapper);
    }
}
