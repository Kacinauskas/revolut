package com.laurynas.kacinauskas.revolut.dto;

public class ResultDTO<T> {

    private T result;

    public ResultDTO() {
    }

    public ResultDTO(T result) {
        this.result = result;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

}
