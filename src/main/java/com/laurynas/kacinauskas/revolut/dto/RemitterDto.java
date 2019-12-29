package com.laurynas.kacinauskas.revolut.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RemitterDto {

    @NotNull
    @Min(value = 1)
    private Long id;

    @NotBlank()
    @Size(min = 10, max = 10)
    private String accountId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

}
