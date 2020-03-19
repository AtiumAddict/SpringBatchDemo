package com.atiumaddict.springbatchdemo.factory.write.lineaggregators;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.transform.FormatterLineAggregator;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * FormatterLineAggregator for fixed length files.
 * It formats dates and dateTimes.
 * It converts null number fields to 0.
 */
@Slf4j
public class FormatterLineAggregatorFixedLengthDefault<T> extends FormatterLineAggregator {

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSS");

    private String format;

    private Locale locale = Locale.getDefault();
    private int maximumLength = 0;
    private int minimumLength = 0;

    public FormatterLineAggregatorFixedLengthDefault() {
    }

    @Override
    public void setMinimumLength(int minimumLength) {
        this.minimumLength = minimumLength;
    }

    @Override
    public void setMaximumLength(int maximumLength) {
        this.maximumLength = maximumLength;
    }

    @Override
    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public void setDateTimeFormatter(DateTimeFormatter dateTimeFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
    }

    public DateTimeFormatter getDateTimeFormatter() {
        return dateTimeFormatter;
    }

    @Override
    protected String doAggregate(Object[] fields) {
        Assert.notNull(this.format, "A format is required");
        for (int i = 0; i < fields.length; i++) {
            if (fields[i] instanceof LocalDateTime) {
                String formattedLocalDateTime = ((LocalDateTime) fields[i]).format(dateTimeFormatter);
                fields[i] = formattedLocalDateTime;
            } else if (fields[i] instanceof LocalDate) {
                LocalDate localDate = (LocalDate) fields[i];
                if (localDate.isBefore(LocalDate.of(1910, 1, 1))) {
                    fields[i] = "0001-01-01";
                }
            } else if (
                    fields[i] == null &&
                            (
                                    fields[i] instanceof Integer ||
                                            fields[i] instanceof Long ||
                                            fields[i] instanceof Short ||
                                            fields[i] instanceof Double ||
                                            fields[i] instanceof Float)
            ) {
                fields[i] = 0;
            } else if (fields[i] == null && fields[i] instanceof BigDecimal) {
                fields[i] = BigDecimal.ZERO;
            }
        }

        String value = String.format(this.locale, this.format, fields);
        if (this.maximumLength > 0) {
            Assert.state(value.length() <= this.maximumLength, String.format("String overflowed in formatter - longer than %d characters: [%s", this.maximumLength, value));
        }

        if (this.minimumLength > 0) {
            Assert.state(value.length() >= this.minimumLength, String.format("String underflowed in formatter - shorter than %d characters: [%s", this.minimumLength, value));
        }

        return value;
    }
}
