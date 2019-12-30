package com.laurynas.kacinauskas.revolut.dao;

import com.laurynas.kacinauskas.revolut.domain.Customer;
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
public class CustomerDao implements Dao<CompletableFuture<Customer>, Long> {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerDao.class);

    @Override
    public CompletableFuture<Customer> getById(Long id) {
        return CompletableFuture.supplyAsync(() -> {
            Session session = SessionUtil.getSession().openSession();
            Transaction transaction = session.beginTransaction();

            Customer customer = null;
            try {
                customer = session.get(Customer.class, id);
                transaction.commit();
            } catch (Exception e) {
                LOG.error("Could not get customer by id: ", e);
                transaction.rollback();
            } finally {
                if (session.isOpen()) {
                    session.close();
                }
            }

            return Optional.ofNullable(customer)
                    .orElseThrow(() -> new GeneralValidationException(ErrorCode.CUSTOMER_NOT_FOUND));
        });
    }

}
