package com.plaxa.util;

import com.plaxa.converter.BirthdayConverter;
import com.plaxa.entity.Audit;
import com.plaxa.entity.Revision;
import com.plaxa.interceptor.GlobalInterceptor;
import com.plaxa.listener.AuditTableListener;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;

@UtilityClass
public class HibernateUtil {

    public static SessionFactory buildSessionFactory() {
        Configuration configuration = buildConfiguration();
        configuration.configure();

        var factory = configuration.buildSessionFactory();
//        registerListeners(factory);

        return factory;
    }

    private static void registerListeners(SessionFactory factory) {
        var sessionFactoryIml = factory.unwrap(SessionFactoryImpl.class);

        var listenerRegistry = sessionFactoryIml.getServiceRegistry().getService(EventListenerRegistry.class);

        var auditTableListener = new AuditTableListener();
        listenerRegistry.appendListeners(EventType.PRE_INSERT, auditTableListener);
        listenerRegistry.appendListeners(EventType.PRE_DELETE, auditTableListener);
    }

    public static Configuration buildConfiguration() {
        Configuration configuration = new Configuration();
//        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
//        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Audit.class);
        configuration.addAnnotatedClass(Revision.class);
        configuration.addAttributeConverter(new BirthdayConverter());
        configuration.registerTypeOverride(new JsonBinaryType());
        configuration.setInterceptor(new GlobalInterceptor());
        return configuration;
    }
}
