package com.momsitter.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Converter
public class LocalDateAttributeConverter implements AttributeConverter<LocalDate, String> {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Override
    public String convertToDatabaseColumn(LocalDate attribute) {
        return attribute.format(DATE_TIME_FORMATTER);
    }

    @Override
    public LocalDate convertToEntityAttribute(String dbData) {
        return LocalDate.parse(dbData, DATE_TIME_FORMATTER);
    }
}
