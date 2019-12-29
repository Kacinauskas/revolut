package com.laurynas.kacinauskas.revolut.dao;

import com.laurynas.kacinauskas.revolut.domain.Account;
import com.laurynas.kacinauskas.revolut.exception.ErrorCode;
import com.laurynas.kacinauskas.revolut.exception.GeneralValidationException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.inject.Singleton;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Singleton
public class AccountDao implements Dao<CompletableFuture<Account>, String> {

    @Override
    public CompletableFuture<Account> getById(String id) {
        return CompletableFuture.supplyAsync(() -> {
            Session session = SessionUtil.getSession().openSession();
            Transaction transaction = session.beginTransaction();

            Account account = null;
            try {
                account = session.get(Account.class, id);
                transaction.commit();
            } catch (Exception e) {
                // TODO: add logging and proper handling
                transaction.rollback();
            } finally {
                if (session.isOpen()) {
                    session.close();
                }
            }

            return Optional.ofNullable(account)
                    .orElseThrow(() -> new GeneralValidationException(ErrorCode.ACCOUNT_NOT_FOUND));
        });
    }

    public void save(Account remitterAccount, Account beneficiaryAccount) {
        Session session = SessionUtil.getSession().openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.saveOrUpdate(remitterAccount);
            session.saveOrUpdate(beneficiaryAccount);
            transaction.commit();
        } catch (Exception e) {
            // TODO: add logging and proper handling
            transaction.rollback();
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
    }

}
