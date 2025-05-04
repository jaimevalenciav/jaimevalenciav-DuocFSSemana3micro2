package com.duocuc.eventospet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EventosNotFoundException extends RuntimeException {

    public EventosNotFoundException(Long id) {
        super("No se ha encontrado en nuestra base de datos los datos del evento con ID: " + id);
    }

    public EventosNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
