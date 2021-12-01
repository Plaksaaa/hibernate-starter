package com.plaxa.runner;

import com.plaxa.dao.CompanyRepository;
import com.plaxa.dao.UserRepository;
import com.plaxa.dto.UserCreateDto;
import com.plaxa.entity.PersonalInfo;
import com.plaxa.entity.Role;
import com.plaxa.interceptor.TransactionInterceptor;
import com.plaxa.mapper.CompanyReadMapper;
import com.plaxa.mapper.UserCreateMapper;
import com.plaxa.mapper.UserReadMapper;
import com.plaxa.service.UserService;
import com.plaxa.util.HibernateUtil;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.time.LocalDate;

public class RepositoryRunner {

    @Transactional
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        try (var factory = HibernateUtil.buildSessionFactory()) {
//            var session = factory.getCurrentSession();

            var session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                    (proxy, method, args1) -> method.invoke(factory.getCurrentSession(), args1));

//            session.beginTransaction();

//            var paymentRepository = new PaymentRepository(session);

            var companyRepository = new CompanyRepository(session);

            var companyReadMapper = new CompanyReadMapper();
            var userReadMapper = new UserReadMapper(companyReadMapper);
            var userCreateMapper = new UserCreateMapper(companyRepository);

            var userRepository = new UserRepository(session);
//            var userService = new UserService(userRepository, userReadMapper, userCreateMapper);
            var transactionInterceptor = new TransactionInterceptor(factory);

            UserService userService = new ByteBuddy()
                    .subclass(UserService.class)
                    .method(ElementMatchers.any())
                    .intercept(MethodDelegation.to(transactionInterceptor))
                    .make()
                    .load(UserService.class.getClassLoader())
                    .getLoaded()
                    .getDeclaredConstructor(UserRepository.class, UserReadMapper.class, UserCreateMapper.class)
                    .newInstance(userRepository, userReadMapper, userCreateMapper);

            userService.findById(1L).ifPresent(System.out::println);

            UserCreateDto userCreateDto = new UserCreateDto(
                    PersonalInfo.builder()
                            .firstname("Liza")
                            .lastname("Face")
                            .birthDate(LocalDate.of(2003, 7, 17))
                            .build(),
                    "Face's Girl",
                    null,
                    Role.USER,
                    1
            );
            userService.create(userCreateDto);

//            paymentRepository.findById(1L).ifPresent(System.out::println);

//            session.getTransaction().commit();
        }
    }
}

