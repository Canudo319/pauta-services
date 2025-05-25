package com.caina.pautaservices.config.utils.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class BooleanConverter implements AttributeConverter<Boolean, Integer>{

    @Override
    public Integer convertToDatabaseColumn(Boolean attribute) {
        if(attribute == null){
            return null;
        }

        return attribute ? 1 : 0;
    }

    @Override
    public Boolean convertToEntityAttribute(Integer dbData) {
        if(dbData == null){
            return null;
        }

        return switch (dbData) {
            case 0 -> false;
            case 1 -> true;
            default -> null;
        };
    }
    
}
