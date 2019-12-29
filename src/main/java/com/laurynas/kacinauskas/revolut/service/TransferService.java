package com.laurynas.kacinauskas.revolut.service;

import com.laurynas.kacinauskas.revolut.dao.AccountDao;
import com.laurynas.kacinauskas.revolut.dao.CustomerDao;
import com.laurynas.kacinauskas.revolut.domain.Account;
import com.laurynas.kacinauskas.revolut.domain.Customer;
import com.laurynas.kacinauskas.revolut.domain.Transfer;
import com.laurynas.kacinauskas.revolut.dto.TransferDto;
import com.laurynas.kacinauskas.revolut.exception.ErrorCode;
import com.laurynas.kacinauskas.revolut.exception.GeneralValidationException;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.CompletableFuture;

@Singleton
public class TransferService {

    private final CustomerDao customerDao;
    private final AccountDao accountDao;

    @Inject
    public TransferService(CustomerDao customerDao, AccountDao accountDao) {
        this.customerDao = customerDao;
        this.accountDao = accountDao;
    }

    public Transfer doTransfer(final TransferDto transferDTO) {
        CompletableFuture<Customer> remitterFuture = customerDao.getById(transferDTO.getRemitter().getId());

        CompletableFuture<Account> beneficiaryAccountFuture = accountDao
                .getById(transferDTO.getBeneficiary().getAccountId());

        return remitterFuture.thenCombine(beneficiaryAccountFuture, (remitter, beneficiaryAccount) -> {
            Account remitterAccount = remitter.getAccounts()
                    .stream()
                    .filter(acc -> acc.getId().equals(transferDTO.getRemitter().getAccountId()))
                    .findFirst()
                    .orElseThrow(() -> new GeneralValidationException(ErrorCode.ACCOUNT_NOT_FOUND));

            checkIfBeneficiaryNameMatches(beneficiaryAccount, transferDTO.getBeneficiary().getName());

            return makeTransferIfBalanceSufficient(remitterAccount, beneficiaryAccount, transferDTO.getAmount(),
                    transferDTO.getDetails());
        }).join();
    }

    private static void checkIfBeneficiaryNameMatches(Account beneficiaryAccount, String providedBeneficiaryName) {
        if (!beneficiaryAccount.getCustomer().getName().equalsIgnoreCase(providedBeneficiaryName)) {
            throw new GeneralValidationException(ErrorCode.BENEFICIARY_NAME_DOES_NOT_MATCH);
        }
    }

    private Transfer makeTransferIfBalanceSufficient(Account remitterAccount, Account beneficiaryAccount,
                                                     Double amount, String details) {
        if (remitterAccount.getBalance() >= amount) {
            Transfer remitterTransfer = new Transfer(beneficiaryAccount.getCustomer(), remitterAccount.getCustomer(),
                    amount, details, remitterAccount);
            Transfer beneficiaryTransfer = new Transfer(beneficiaryAccount.getCustomer(), remitterAccount.getCustomer(),
                    amount, details, beneficiaryAccount);

            remitterAccount.setBalance(remitterAccount.getBalance() - amount);
            beneficiaryAccount.setBalance(beneficiaryAccount.getBalance() + amount);
            remitterAccount.getTransfers().add(remitterTransfer);
            beneficiaryAccount.getTransfers().add(beneficiaryTransfer);

            accountDao.update(remitterAccount, beneficiaryAccount);

            return remitterTransfer;
        }

        throw new GeneralValidationException(ErrorCode.INSUFFICIENT_BALANCE);
    }

}
