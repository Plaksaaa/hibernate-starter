package com.plaxa.runner;

import com.plaxa.dao.PaymentRepository;
import com.plaxa.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.lang.reflect.Proxy;

public class RepositoryRunner {

    public static void main(String[] args) {

        try (var factory = HibernateUtil.buildSessionFactory()) {
//            var session = factory.getCurrentSession();

            var session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                    (proxy, method, args1) -> method.invoke(factory.getCurrentSession(), args1));

            session.beginTransaction();

            var paymentRepository = new PaymentRepository(session);

            paymentRepository.findById(1L).ifPresent(System.out::println);

            session.getTransaction().commit();
        }
    }
}

