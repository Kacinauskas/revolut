package com.laurynas.kacinauskas.revolut.dto;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class TransferDTO {

    @Valid
    private RemitterDTO remitter;

    @Valid
    private BeneficiaryDTO beneficiary;

    @Min(value = 0)
    private Double amount;

    @NotBlank
    @Size(min = 5, max = 100)
    private String details;

    public RemitterDTO getRemitter() {
        return remitter;
    }

    public void setRemitter(RemitterDTO remitter) {
        this.remitter = remitter;
    }

    public BeneficiaryDTO getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(BeneficiaryDTO beneficiary) {
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
