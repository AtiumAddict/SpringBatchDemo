package com.atiumaddict.springbatchdemo.factory.write.lineaggregators;

import org.springframework.batch.item.file.transform.DelimitedLineAggregator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * CSV DelimitedLineAggregator that formats dates dateTimes.
 */
public class DelimitedLineAggregatorCSVDefault<T> extends DelimitedLineAggregator<T> {

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSS");

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private String delimiter = ",";

    private char quoteCharacter = '"';

    public DelimitedLineAggregatorCSVDefault() {
        setDelimiter(";");
    }

    /**
     * Public setter for the delimiter.
     *
     * @param sDelimiter the delimiter to set
     */
    @Override
    public void setDelimiter(String sDelimiter) {
        delimiter = sDelimiter;
    }

    public void setQuoteCharacter(char sQuoteCharacter) {
        quoteCharacter = sQuoteCharacter;
    }

    @Override
    public String doAggregate(Object[] fields) {
        StringBuilder aStringBuilder = new StringBuilder();
        int nbFields = (fields.length - 1);
        for (int i = 0; i <= nbFields; i++) {
            if (fields[i] instanceof LocalDateTime)
                aStringBuilder.append(((LocalDateTime) fields[i]).format(dateTimeFormatter));
            else if (fields[i] instanceof LocalDate)
                aStringBuilder.append(((LocalDate) fields[i]).format(dateFormatter));
            else
                aStringBuilder.append(fields[i]);

            if (i < nbFields) {
                aStringBuilder.append(delimiter);
            }
        }
        return aStringBuilder.toString();
    }
}
