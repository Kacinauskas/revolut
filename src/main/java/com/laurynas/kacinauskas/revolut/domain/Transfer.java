package com.laurynas.kacinauskas.revolut.domain;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transfer")
public class Transfer {

    public Transfer() {
    }

    public Transfer(Customer beneficiary, Customer remitter, Double amount, String details, Account account) {
        this.beneficiary = beneficiary;
        this.remitter = remitter;
        this.amount = amount;
        this.details = details;
        this.account = account;
    }

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name = "beneficiary_id")
    private Customer beneficiary;

    @OneToOne
    @JoinColumn(name = "remitter_id")
    private Customer remitter;

    private Double amount;

    private String details;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Customer getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(Customer beneficiary) {
        this.beneficiary = beneficiary;
    }

    public Customer getRemitter() {
        return remitter;
    }

    public void setRemitter(Customer remitter) {
        this.remitter = remitter;
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
    
}
