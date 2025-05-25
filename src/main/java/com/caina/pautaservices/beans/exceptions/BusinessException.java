package com.caina.pautaservices.beans.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.AllArgsConstructor;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
@AllArgsConstructor
public class BusinessException extends RuntimeException {
    
    private final String msg;

    @Override
    public String getMessage() {
        return msg;
    }

}
