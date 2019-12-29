package com.laurynas.kacinauskas.revolut.dto;

public class ResultDto<T> {

    private T result;

    public ResultDto() {
    }

    public ResultDto(T result) {
        this.result = result;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

}
