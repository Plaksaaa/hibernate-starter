package com.plaxa;

import com.plaxa.entity.Chat;
import com.plaxa.entity.Company;
import com.plaxa.entity.Profile;
import com.plaxa.entity.User;
import com.plaxa.util.HibernateUtil;
import lombok.Cleanup;
import org.junit.jupiter.api.Test;

import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.joining;

class HibernateRunnerTest {

    @Test
    void checkManyToMany() {
        try (var factory = HibernateUtil.buildSessionFactory();
             var session = factory.openSession()) {

            session.beginTransaction();

            var user = session.get(User.class, 3L);

            var chat = Chat.builder()
                    .name("LolchikChat")
                    .build();

            user.addChat(chat);
            session.save(chat);

            session.getTransaction().commit();
        }
    }

    @Test
    void checkOneToOne() {
        try (var factory = HibernateUtil.buildSessionFactory();
             var session = factory.openSession()) {

            session.beginTransaction();

            var user =
                    User.builder()
                            .username("test@gmail.com")
                            .build();

            var profile = Profile.builder()
                    .language("RU")
                    .street("Coloss 18")
                    .build();

            profile.setUser(user);
            session.save(user);

            session.getTransaction().commit();
        }
    }

    @Test
    void checkOrphanRemoval() {
        try (var factory = HibernateUtil.buildSessionFactory();
             var session = factory.openSession()) {

            session.beginTransaction();

            var company = session.getReference(Company.class, 1);
//            нужно удалить cascade type all

            company.getUsers().removeIf(user -> user.getId().equals(3L));

            session.getTransaction().commit();
        }
    }

    @Test
    void checkLazyInitialization() {
        Company company = null;
        try (var factory = HibernateUtil.buildSessionFactory();
             var session = factory.openSession()) {

            session.beginTransaction();

            company = session.get(Company.class, 1);

            session.getTransaction().commit();
        }
        var users = company.getUsers();
        System.out.println(users.size());
    }

    @Test
    void deleteCompany() {
        @Cleanup var factory = HibernateUtil.buildSessionFactory();
        @Cleanup var session = factory.openSession();

        session.beginTransaction();

        var company = session.get(Company.class, 3);
        session.delete(company);

        session.getTransaction().commit();
    }

    @Test
    void addUserToNewCompany() {
        @Cleanup var factory = HibernateUtil.buildSessionFactory();
        @Cleanup var session = factory.openSession();

        session.beginTransaction();

        var company = Company.builder()
                .name("Facebook")
                .build();

        var user = User.builder()
                .username("Vitaly@gmail.com")
                .build();

//        user.setCompany(company);
//        company.getUsers().add(user);
        company.addUser(user);

//        так как cascade type all то сохранятся и юзеры в этой компании
        session.save(company);

        session.getTransaction().commit();
    }

    @Test
    void oneToMany() {
        @Cleanup var factory = HibernateUtil.buildSessionFactory();
        @Cleanup var session = factory.openSession();

        session.beginTransaction();

        var company = session.get(Company.class, 1);
        System.out.println(company);

        session.getTransaction().commit();
    }

    @Test
    void checkReflectionApi() throws SQLException, IllegalAccessException {
        User user = User.builder()
                .build();

        String sql = """
                insert
                into 
                %s
                (%s)
                values
                (%s)
                """;
        var tableName = ofNullable(user.getClass().getAnnotation(Table.class))
                .map(tableAnnotation -> tableAnnotation.schema() + "." + tableAnnotation.name())
                .orElse(user.getClass().getName());

        var declaredFields = user.getClass().getDeclaredFields();

        var columnNames = Arrays.stream(declaredFields)
                .map(field -> ofNullable(field.getAnnotation(Column.class))
                        .map(Column::name)
                        .orElse(field.getName()))
                .collect(joining(", "));

        var columnValues = Arrays.stream(declaredFields)
                .map(field -> "?")
                .collect(joining(", "));

        System.out.println(sql.formatted(tableName, columnNames, columnValues));

        Connection connection = null;

        try (var preparedStatement = connection.prepareStatement(sql)) {
            for (Field declaredField : declaredFields) {
                declaredField.setAccessible(true);
                preparedStatement.setObject(1, declaredField.get(user));
            }
        }
    }
}