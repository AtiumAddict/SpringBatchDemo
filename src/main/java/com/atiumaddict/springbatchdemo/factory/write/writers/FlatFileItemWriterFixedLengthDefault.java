package com.atiumaddict.springbatchdemo.factory.write.writers;

import com.atiumaddict.springbatchdemo.factory.write.lineaggregators.FormatterLineAggregatorFixedLengthDefault;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.core.io.Resource;

public class FlatFileItemWriterFixedLengthDefault<T> extends FlatFileItemWriter {
    public FlatFileItemWriterFixedLengthDefault(Resource resource) {
        setResource(resource);
        setShouldDeleteIfExists(true);
        setAppendAllowed(false);
        setEncoding("ISO-8859-7");
    }

    public FlatFileItemWriterFixedLengthDefault(Resource resource, String format, String[] names) {
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
