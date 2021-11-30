package com.plaxa.runner;

import com.plaxa.interceptor.GlobalInterceptor;
import com.plaxa.util.HibernateUtil;
import com.plaxa.util.TestDataImporter;

public class InterceptorRunner {

    public static void main(String[] args) {

        try (var factory = HibernateUtil.buildSessionFactory();
             var session = factory
                     .withOptions()
                     .interceptor(new GlobalInterceptor())
                     .openSession()) {
            TestDataImporter.importData(factory);

            session.getTransaction().commit();
        }
    }
}
