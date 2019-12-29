package com.laurynas.kacinauskas.revolut.dto;

public class TransferDTO {

    private RemitterDTO remitter;
    private BeneficiaryDTO beneficiary;

    private Double amount;

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
