package com.laurynas.kacinauskas.revolut.dto;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class TransferDto {

    @Valid
    private RemitterDto remitter;

    @Valid
    private BeneficiaryDto beneficiary;

    @Min(value = 0)
    private Double amount;

    @NotBlank
    @Size(min = 5, max = 100)
    private String details;

    public RemitterDto getRemitter() {
        return remitter;
    }

    public void setRemitter(RemitterDto remitter) {
        this.remitter = remitter;
    }

    public BeneficiaryDto getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(BeneficiaryDto beneficiary) {
        this.beneficiary = beneficiary;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

}
