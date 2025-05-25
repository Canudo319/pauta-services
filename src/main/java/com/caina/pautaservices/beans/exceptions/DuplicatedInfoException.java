package com.caina.pautaservices.beans.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.AllArgsConstructor;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
@AllArgsConstructor
public class DuplicatedInfoException extends RuntimeException {

    private final String recurso;
    private final Object valor;

    @Override
    public String getMessage() {
        return String.format("%s duplicado: %s", recurso, valor.toString());
    }
}