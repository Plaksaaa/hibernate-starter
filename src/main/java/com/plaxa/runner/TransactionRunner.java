package com.plaxa.runner;

import com.plaxa.entity.Payment;
import com.plaxa.util.HibernateUtil;
import com.plaxa.util.TestDataImporter;

import javax.persistence.LockModeType;
import javax.transaction.Transactional;

public class TransactionRunner {

    @Transactional
    public static void main(String[] args) {

        try (var factory = HibernateUtil.buildSessionFactory();
             var session = factory.openSession()) {
            TestDataImporter.importData(factory);

//            session.setDefaultReadOnly(true);
            session.beginTransaction();

            session.createNativeQuery("SET TRANSACTION READ ONLY;").executeUpdate();

           /* session.createQuery("select p from Payment p", Payment.class)
//                    .setLockMode(LockModeType.PESSIMISTIC_FORCE_INCREMENT)
//                    .setHint("javax.persistence.lock.timeout", 5000)
                    .setReadOnly(true)
                    .list();*/

            var payment = session.find(Payment.class, 1L, LockModeType.OPTIMISTIC);
            payment.setAmount(payment.getAmount() + 10);

            session.getTransaction().commit();
        }
    }
}
/* try {
                var transaction = session.beginTransaction();

                var payment = session.get(Payment.class, 1L);
                var payment1 = session.get(Payment.class, 2L);

                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw e;
            }*/