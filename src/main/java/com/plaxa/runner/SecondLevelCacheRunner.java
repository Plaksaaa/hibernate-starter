package com.plaxa.runner;

import com.plaxa.entity.Payment;
import com.plaxa.entity.User;
import com.plaxa.util.HibernateUtil;
import org.hibernate.SessionFactory;

import javax.transaction.Transactional;

public class SecondLevelCacheRunner {

    @Transactional
    public static void main(String[] args) {
        try (SessionFactory factory = HibernateUtil.buildSessionFactory()) {
            User user = null;
            try (var session = factory.openSession()) {
                session.beginTransaction();

                user = session.find(User.class, 1);
                var user1 = session.find(User.class, 1);

                session.createQuery("select p from Payment p where p.receiver.id = :userId", Payment.class)
                        .setParameter("userId", 1L)
                        .setCacheable(true)
//                        .setCacheRegion("queries")
                        .uniqueResult();

                session.getTransaction().commit();
            }

            try (var session2 = factory.openSession()) {
                session2.beginTransaction();

                var user2 = session2.find(User.class, 1);

                session2.getTransaction().commit();
            }
        }
    }
}
