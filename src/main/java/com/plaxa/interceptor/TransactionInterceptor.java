package com.plaxa.interceptor;

import lombok.RequiredArgsConstructor;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.transaction.Transactional;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

@RequiredArgsConstructor
public class TransactionInterceptor {

    private final SessionFactory sessionFactory;

    @RuntimeType
    public Object intercept(@SuperCall Callable<Object> methodCall, @Origin Method method) throws Exception {
        Transaction transaction = null;
        boolean transactionStarted = false;
        if (method.isAnnotationPresent(Transactional.class)) {
            transaction = sessionFactory.getCurrentSession().getTransaction();
            if (!transaction.isActive()) {
                transaction.begin();
                transactionStarted = true;
            }
        }

        Object methodResult;
        try {
            methodResult = methodCall.call();

            if (transactionStarted) {
                transaction.commit();
            }
        } catch (Exception exception) {
            if (transactionStarted) {
                transaction.rollback();
            }
            throw exception;
        }
        return methodResult;
    }
}
