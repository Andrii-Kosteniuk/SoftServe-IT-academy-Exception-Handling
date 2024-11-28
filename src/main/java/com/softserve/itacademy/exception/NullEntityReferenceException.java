package com.softserve.itacademy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class NullEntityReferenceException extends RuntimeException{
    public NullEntityReferenceException(String message) {
        super(message);
    }

}
