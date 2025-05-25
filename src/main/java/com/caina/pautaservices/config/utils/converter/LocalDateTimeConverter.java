package com.caina.pautaservices.config.utils.converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, String>{

    private final static DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public String convertToDatabaseColumn(LocalDateTime attribute) {
        if(attribute == null){
            return null;
        }

        return DTF.format(attribute);
    }

    @Override
    public LocalDateTime convertToEntityAttribute(String dbData) {
        if(dbData == null){
            return null;
        }

        return LocalDateTime.parse(dbData, DTF);
    }
    
}
