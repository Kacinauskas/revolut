package com.laurynas.kacinauskas.revolut.exception;

public class GeneralError {

    private String code;

    public GeneralError() {
    }

    public GeneralError(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
