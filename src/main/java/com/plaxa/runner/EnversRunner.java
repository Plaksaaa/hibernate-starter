package com.plaxa.runner;

import com.plaxa.entity.Payment;
import com.plaxa.util.HibernateUtil;
import org.hibernate.ReplicationMode;
import org.hibernate.SessionFactory;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;

import javax.transaction.Transactional;

public class EnversRunner {

    @Transactional
    public static void main(String[] args) {
        try (SessionFactory factory = HibernateUtil.buildSessionFactory()) {

            try (var session = factory.openSession()) {
                session.beginTransaction();

                var payment = session.find(Payment.class, 1);
                payment.setAmount(payment.getAmount() + 10);

                session.getTransaction().commit();
            }

            try (var session2 = factory.openSession()) {
                session2.beginTransaction();

                var auditReader = AuditReaderFactory.get(session2);
                var oldPayment = auditReader.find(Payment.class, 1L, 1L);

//                auditReader.find(Payment.class, 1L, 1L)
                session2.replicate(oldPayment, ReplicationMode.OVERWRITE);

                auditReader.createQuery()
                        .forEntitiesAtRevision(Payment.class, 400L)
                        .add(AuditEntity.property("amount").ge(450))
                        .add(AuditEntity.property("id").ge(6L))
                        .addProjection(AuditEntity.property("amount"))
                        .addProjection(AuditEntity.id())
                        .getResultList();

                session2.getTransaction().commit();
            }
        }
    }
}
