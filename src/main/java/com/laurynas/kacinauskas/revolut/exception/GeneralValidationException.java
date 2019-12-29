package com.laurynas.kacinauskas.revolut.exception;

public class GeneralValidationException extends RuntimeException {

    public GeneralValidationException(ExceptionCode exceptionCode) {
        super(exceptionCode.toString());
    }

}
