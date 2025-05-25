package com.caina.pautaservices.beans.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.AllArgsConstructor;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
@AllArgsConstructor
public class NotFoundException extends RuntimeException {

    private final String recurso;
    private final Object valor;

    @Override
    public String getMessage() {
        return String.format("%s não encontado: %s", recurso, valor.toString());
    }
}