package com.atiumaddict.springbatchdemo.factory.write.writers;

import com.atiumaddict.springbatchdemo.factory.write.lineaggregators.DelimitedLineAggregatorCSVDefault;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.core.io.Resource;

public class FlatFileItemWriterCsvDefault<T> extends FlatFileItemWriter {
    /**
     * Default FlatFileItemWriter for CSV files.
     * The encoding is "ISO-8859-7".
     * {@link DelimitedLineAggregatorCSVDefault} is used.
     */
    public FlatFileItemWriterCsvDefault(Resource outputResource, String[] names) {
        setResource(outputResource);
        setAppendAllowed(false);
        setEncoding("ISO-8859-7");
        DelimitedLineAggregator delimitedLineAggregator = new DelimitedLineAggregatorCSVDefault();
        BeanWrapperFieldExtractor beanWrapperFieldExtractor = new BeanWrapperFieldExtractor<T>();
        beanWrapperFieldExtractor.setNames(names);
        delimitedLineAggregator.setFieldExtractor(beanWrapperFieldExtractor);
        setLineAggregator(delimitedLineAggregator);
    }
}
