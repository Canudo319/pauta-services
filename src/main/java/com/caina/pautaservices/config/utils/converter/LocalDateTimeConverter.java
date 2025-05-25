package com.caina.pautaservices.config.utils.converter;

import java.time.LocalDateTime;

import com.caina.pautaservices.config.utils.date.DateUtils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, String>{

    @Override
    public String convertToDatabaseColumn(LocalDateTime attribute) {
        if(attribute == null){
            return null;
        }

        return DateUtils.UNIVERSAL_DTF.format(attribute);
    }

    @Override
    public LocalDateTime convertToEntityAttribute(String dbData) {
        if(dbData == null){
            return null;
        }

        return LocalDateTime.parse(dbData, DateUtils.UNIVERSAL_DTF);
    }
    
}
