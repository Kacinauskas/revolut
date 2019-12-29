package com.laurynas.kacinauskas.revolut.exception;

public class GeneralValidationException extends RuntimeException {

    public GeneralValidationException(ErrorCode errorCode) {
        super(errorCode.toString());
    }

}
