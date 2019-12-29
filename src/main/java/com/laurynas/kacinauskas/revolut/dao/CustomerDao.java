package com.laurynas.kacinauskas.revolut.dao;

import com.laurynas.kacinauskas.revolut.domain.Customer;
import com.laurynas.kacinauskas.revolut.exception.ExceptionCode;
import com.laurynas.kacinauskas.revolut.exception.GeneralValidationException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class CustomerDao implements Dao<CompletableFuture<Customer>, Long> {

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
                // TODO: add logging
                transaction.rollback();
            } finally {
                if (session.isOpen()) {
                    session.close();
                }
            }

            return Optional.ofNullable(customer)
                    .orElseThrow(() -> new GeneralValidationException(ExceptionCode.CUSTOMER_NOT_FOUND));
        });
    }

}
