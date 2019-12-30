package com.laurynas.kacinauskas.revolut.service

import com.laurynas.kacinauskas.revolut.dao.AccountDao
import com.laurynas.kacinauskas.revolut.dao.CustomerDao
import com.laurynas.kacinauskas.revolut.domain.Account
import com.laurynas.kacinauskas.revolut.domain.Customer
import com.laurynas.kacinauskas.revolut.domain.Transfer
import com.laurynas.kacinauskas.revolut.dto.BeneficiaryDto
import com.laurynas.kacinauskas.revolut.dto.RemitterDto
import com.laurynas.kacinauskas.revolut.dto.TransferDto
import com.laurynas.kacinauskas.revolut.exception.ErrorCode
import spock.lang.Specification
import spock.lang.Subject

import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionException

class TransferServiceSpec extends Specification {

    @Subject
    private TransferService transferService

    private CustomerDao customerDao = Mock()
    private AccountDao accountDao = Mock()

    def setup() {
        transferService = new TransferService(customerDao, accountDao)
    }

    def "should throw exception if provided remitter's account does not exist"() {
        given:
            RemitterDto remitterDto = new RemitterDto()
            remitterDto.setId(1L)
            remitterDto.setAccountId("1234567890")

            BeneficiaryDto beneficiaryDto = new BeneficiaryDto()
            beneficiaryDto.setName("Maisey Christian")
            beneficiaryDto.setAccountId("1357924688")

            TransferDto transferDto = new TransferDto()
            transferDto.setRemitter(remitterDto)
            transferDto.setBeneficiary(beneficiaryDto)
            transferDto.setAmount(2000D)
            transferDto.setDetails("To do some business")

            Customer remitter = new Customer()
            remitter.setId(1L)
            remitter.setName("Debra Bain")
            Account remitterAccount = new Account()
            remitterAccount.setId("1238908761")
            remitterAccount.setBalance(5000D)
            remitterAccount.setCustomer(remitter)
            Set<Account> remitterAccounts = new LinkedHashSet<>()
            remitterAccounts.add(remitterAccount)
            remitter.setAccounts(remitterAccounts)

            Customer beneficiary = new Customer()
            beneficiary.setId(2L)
            beneficiary.setName("Maisey Christian")
            Account beneficiaryAccount = new Account()
            beneficiaryAccount.setId("1357924688")
            beneficiaryAccount.setBalance(100D)
            beneficiaryAccount.setCustomer(beneficiary)
            Set<Account> beneficiaryAccountAccounts = new LinkedHashSet<>()
            beneficiaryAccountAccounts.add(beneficiaryAccount)
            beneficiary.setAccounts(beneficiaryAccountAccounts)

            CompletableFuture<Customer> remitterFuture = CompletableFuture.completedFuture(remitter)
            CompletableFuture<Account> beneficiaryAccountFuture = CompletableFuture.completedFuture(beneficiaryAccount)
        when: "making transaction"
            transferService.doTransfer(transferDto)
        then: "getting remitter's details"
            1 * customerDao.getById(1L) >> remitterFuture
        then: "getting beneficiary's details"
            1 * accountDao.getById("1357924688") >> beneficiaryAccountFuture
        and: "exception is thrown"
            def e = thrown(CompletionException)
            e.getCause().getMessage() == ErrorCode.ACCOUNT_NOT_FOUND.toString()
    }

    def "should throw exception if provided beneficiary's name does not match"() {
        given:
            RemitterDto remitterDto = new RemitterDto()
            remitterDto.setId(1L)
            remitterDto.setAccountId("1234567890")

            BeneficiaryDto beneficiaryDto = new BeneficiaryDto()
            beneficiaryDto.setName("Maisey Williams")
            beneficiaryDto.setAccountId("1357924688")

            TransferDto transferDto = new TransferDto()
            transferDto.setRemitter(remitterDto)
            transferDto.setBeneficiary(beneficiaryDto)
            transferDto.setAmount(2000D)
            transferDto.setDetails("To do some business")

            Customer remitter = new Customer()
            remitter.setId(1L)
            remitter.setName("Debra Bain")
            Account remitterAccount = new Account()
            remitterAccount.setId("1234567890")
            remitterAccount.setBalance(5000D)
            remitterAccount.setCustomer(remitter)
            Set<Account> remitterAccounts = new LinkedHashSet<>()
            remitterAccounts.add(remitterAccount)
            remitter.setAccounts(remitterAccounts)

            Customer beneficiary = new Customer()
            beneficiary.setId(2L)
            beneficiary.setName("Maisey Christian")
            Account beneficiaryAccount = new Account()
            beneficiaryAccount.setId("1357924688")
            beneficiaryAccount.setBalance(100D)
            beneficiaryAccount.setCustomer(beneficiary)
            Set<Account> beneficiaryAccountAccounts = new LinkedHashSet<>()
            beneficiaryAccountAccounts.add(beneficiaryAccount)
            beneficiary.setAccounts(beneficiaryAccountAccounts)

            CompletableFuture<Customer> remitterFuture = CompletableFuture.completedFuture(remitter)
            CompletableFuture<Account> beneficiaryAccountFuture = CompletableFuture.completedFuture(beneficiaryAccount)
        when: "making transaction"
            transferService.doTransfer(transferDto)
        then: "getting remitter's details"
            1 * customerDao.getById(1L) >> remitterFuture
        then: "getting beneficiary's details"
            1 * accountDao.getById("1357924688") >> beneficiaryAccountFuture
        and: "exception is thrown"
            def e = thrown(CompletionException)
            e.getCause().getMessage() == ErrorCode.BENEFICIARY_NAME_DOES_NOT_MATCH.toString()
    }

    def "should throw exception if balance insufficient"() {
        given:
            RemitterDto remitterDto = new RemitterDto()
            remitterDto.setId(1L)
            remitterDto.setAccountId("1234567890")

            BeneficiaryDto beneficiaryDto = new BeneficiaryDto()
            beneficiaryDto.setName("Maisey Christian")
            beneficiaryDto.setAccountId("1357924688")

            TransferDto transferDto = new TransferDto()
            transferDto.setRemitter(remitterDto)
            transferDto.setBeneficiary(beneficiaryDto)
            transferDto.setAmount(20000D)
            transferDto.setDetails("To do some business")

            Customer remitter = new Customer()
            remitter.setId(1L)
            remitter.setName("Debra Bain")
            Account remitterAccount = new Account()
            remitterAccount.setId("1234567890")
            remitterAccount.setBalance(5000D)
            remitterAccount.setCustomer(remitter)
            Set<Account> remitterAccounts = new LinkedHashSet<>()
            remitterAccounts.add(remitterAccount)
            remitter.setAccounts(remitterAccounts)

            Customer beneficiary = new Customer()
            beneficiary.setId(2L)
            beneficiary.setName("Maisey Christian")
            Account beneficiaryAccount = new Account()
            beneficiaryAccount.setId("1357924688")
            beneficiaryAccount.setBalance(100D)
            beneficiaryAccount.setCustomer(beneficiary)
            Set<Account> beneficiaryAccountAccounts = new LinkedHashSet<>()
            beneficiaryAccountAccounts.add(beneficiaryAccount)
            beneficiary.setAccounts(beneficiaryAccountAccounts)

            CompletableFuture<Customer> remitterFuture = CompletableFuture.completedFuture(remitter)
            CompletableFuture<Account> beneficiaryAccountFuture = CompletableFuture.completedFuture(beneficiaryAccount)
        when: "making transaction"
            transferService.doTransfer(transferDto)
        then: "getting remitter's details"
            1 * customerDao.getById(1L) >> remitterFuture
        then: "getting beneficiary's details"
            1 * accountDao.getById("1357924688") >> beneficiaryAccountFuture
        and: "exception is thrown"
            def e = thrown(CompletionException)
            e.getCause().getMessage() == ErrorCode.INSUFFICIENT_BALANCE.toString()
    }

    def "should make successful transaction"() {
        given:
            RemitterDto remitterDto = new RemitterDto()
            remitterDto.setId(1L)
            remitterDto.setAccountId("1234567890")

            BeneficiaryDto beneficiaryDto = new BeneficiaryDto()
            beneficiaryDto.setName("Maisey Christian")
            beneficiaryDto.setAccountId("1357924688")

            TransferDto transferDto = new TransferDto()
            transferDto.setRemitter(remitterDto)
            transferDto.setBeneficiary(beneficiaryDto)
            transferDto.setAmount(2000D)
            transferDto.setDetails("To do some business")

            Customer remitter = new Customer()
            remitter.setId(1L)
            remitter.setName("Debra Bain")
            Account remitterAccount = new Account()
            remitterAccount.setId("1234567890")
            remitterAccount.setBalance(5000D)
            remitterAccount.setCustomer(remitter)
            Set<Account> remitterAccounts = new LinkedHashSet<>()
            remitterAccounts.add(remitterAccount)
            remitter.setAccounts(remitterAccounts)

            Customer beneficiary = new Customer()
            beneficiary.setId(2L)
            beneficiary.setName("Maisey Christian")
            Account beneficiaryAccount = new Account()
            beneficiaryAccount.setId("1357924688")
            beneficiaryAccount.setBalance(100D)
            beneficiaryAccount.setCustomer(beneficiary)
            Set<Account> beneficiaryAccountAccounts = new LinkedHashSet<>()
            beneficiaryAccountAccounts.add(beneficiaryAccount)
            beneficiary.setAccounts(beneficiaryAccountAccounts)

            CompletableFuture<Customer> remitterFuture = CompletableFuture.completedFuture(remitter)
            CompletableFuture<Account> beneficiaryAccountFuture = CompletableFuture.completedFuture(beneficiaryAccount)
        when: "making transaction"
            Transfer result = transferService.doTransfer(transferDto)
        then: "getting remitter's details"
            1 * customerDao.getById(1L) >> remitterFuture
        then: "getting beneficiary's details"
            1 * accountDao.getById("1357924688") >> beneficiaryAccountFuture
        then: "updating remitter's and beneficiary's account"
            1 * accountDao.update(
                    {
                        Account remiAccount ->
                        remiAccount.getId() == "1234567890" &&
                        remiAccount.getBalance() == 3000L
                    } as Account,
                    {
                        Account beneAccount ->
                        beneAccount.getId() == "1357924688" &&
                        beneAccount.getBalance() == 2100L
                    } as Account)
        and: "evaluating result"
            result.getRemitter() == remitter
            result.getBeneficiary() == beneficiary
            result.getAmount() == 2000D
            result.getDetails() == "To do some business"
            result.getAccount() == remitterAccount
    }

}
