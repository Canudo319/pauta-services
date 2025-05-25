package com.caina.pautaservices.config.utils.date;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.caina.pautaservices.beans.exceptions.BusinessException;

public class DateUtils {
    
    private final static String UNIVERSAL_DTF_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public final static DateTimeFormatter UNIVERSAL_DTF = DateTimeFormatter.ofPattern(UNIVERSAL_DTF_PATTERN);
    
    public static LocalDateTime parseDTF(String date) {
        LocalDateTime dateTime = null;
        try{
            dateTime = LocalDateTime.parse(date, UNIVERSAL_DTF);
        }catch(DateTimeParseException e){
            throw new BusinessException(String.format("Data deve estar no formato: %s", UNIVERSAL_DTF_PATTERN));
        }
        return dateTime;
    }

}
