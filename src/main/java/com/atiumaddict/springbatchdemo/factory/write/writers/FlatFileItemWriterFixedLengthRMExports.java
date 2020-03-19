package com.atiumaddict.springbatchdemo.factory.write.writers;

import com.atiumaddict.springbatchdemo.factory.write.lineaggregators.FormatterLineAggregatorFixedLengthDefault;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.core.io.Resource;

/**
 * Same as the default FlatFileItemWriter for fixed length files, but with a different DateTimeFormatter in the line
 * aggregator, which is appropriate for the reverse migration exports.
 */
public class FlatFileItemWriterFixedLengthRMExports<T> extends FlatFileItemWriter {
    public FlatFileItemWriterFixedLengthRMExports(Resource resource) {
        setResource(resource);
        setShouldDeleteIfExists(true);
        setAppendAllowed(false);
        setEncoding("ISO-8859-7");
    }

    public FlatFileItemWriterFixedLengthRMExports(Resource resource, String format, String[] names) {
        setResource(resource);
        setShouldDeleteIfExists(true);
        setAppendAllowed(false);
        setEncoding("ISO-8859-7");
        setCustomLineAggregator(format, names);
    }

    public void setCustomLineAggregator(String format, String[] names) {
        FormatterLineAggregatorFixedLengthDefault<T> formatterLineAggregator = new FormatterLineAggregatorFixedLengthDefault() {
            {
                setFormat(format);
                setFieldExtractor(new BeanWrapperFieldExtractor<T>() {{
                    setNames(names);
                }});
            }
        };
        setLineAggregator(formatterLineAggregator);
        setLineSeparator("\r\n");
    }
}
