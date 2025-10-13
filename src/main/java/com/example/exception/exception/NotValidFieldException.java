package com.example.exception.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;

public class NotValidFieldException extends MethodArgumentNotValidException {

    public NotValidFieldException(MethodArgumentNotValidException methodArgumentNotValidException) {
        super(methodArgumentNotValidException.getParameter(), methodArgumentNotValidException.getBindingResult());
    }

}
