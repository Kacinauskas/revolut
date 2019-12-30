package com.laurynas.kacinauskas.revolut.dao;

import com.laurynas.kacinauskas.revolut.domain.Account;
import com.laurynas.kacinauskas.revolut.exception.ErrorCode;
import com.laurynas.kacinauskas.revolut.exception.GeneralValidationException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Singleton
public class AccountDao implements Dao<CompletableFuture<Account>, String> {

    private static final Logger LOG = LoggerFactory.getLogger(AccountDao.class);

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
                LOG.error("Could not get account by id: ", e);
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

    public void update(Account remitterAccount, Account beneficiaryAccount) {
        Session session = SessionUtil.getSession().openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.saveOrUpdate(remitterAccount);
            session.saveOrUpdate(beneficiaryAccount);
            transaction.commit();
        } catch (Exception e) {
            LOG.error("Could not update accounts: ", e);
            transaction.rollback();
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
    }

}
