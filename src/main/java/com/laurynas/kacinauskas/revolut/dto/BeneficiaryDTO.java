package com.laurynas.kacinauskas.revolut.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class BeneficiaryDTO {

    @NotBlank
    @Size(max = 50)
    private String name;

    @NotBlank()
    @Size(min = 10, max = 10)
    private String accountId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

}
