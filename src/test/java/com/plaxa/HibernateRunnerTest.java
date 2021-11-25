package com.plaxa;

import com.plaxa.entity.*;
import com.plaxa.util.HibernateTestUtil;
import com.plaxa.util.HibernateUtil;
import lombok.Cleanup;
import org.hibernate.annotations.QueryHints;
import org.junit.jupiter.api.Test;

import javax.persistence.FlushModeType;

class HibernateRunnerTest {

    @Test
    void chekHql() {
        try (var factory = HibernateTestUtil.buildSessionFactory();
             var session = factory.openSession()) {

            session.beginTransaction();

            var userList = session.createQuery(
                    "select u from User u " +
                            "join u.company c " +
                            "where u.personalInfo.firstname = :firstname", User.class)
                    .setParameter("firstname", "nasty")
                    .setFlushMode(FlushModeType.AUTO)
                    .setHint(QueryHints.FETCH_SIZE, "50")
                    .list();

            var countRows = session.createQuery("update User u set u.role = 'ADMIN'")
                    .executeUpdate();

//            session.createNativeQuery("select u.* from users u where u.firstname = 'nasty'", User.class);

            
            session.getTransaction().commit();
        }
    }

    @Test
    void checkH2() {
        try (var factory = HibernateTestUtil.buildSessionFactory();
             var session = factory.openSession()) {

            session.beginTransaction();

            var company = Company.builder()
                    .name("Google")
                    .build();
            session.save(company);

            /*var programmer = Programmer.builder()
                    .username("kolya@gmail.com")
                    .language(Language.JAVA)
                    .company(company)
                    .build();
            session.save(programmer);*/

            /*var manager = Manager.builder()
                    .username("tanya@gmail.com")
                    .projectName("Starter")
                    .company(company)
                    .build();
            session.save(manager);*/
            session.flush();

            session.clear();

            var programmer1 = session.get(Programmer.class, 1);
            var user = session.get(User.class, 1);

            session.getTransaction().commit();
        }
    }

    @Test
    void localeInfo() {
        try (var factory = HibernateUtil.buildSessionFactory();
             var session = factory.openSession()) {

            session.beginTransaction();

            var company = session.get(Company.class, 1);
            company.getUsers().forEach(System.out::println);
//            company.getUsers().forEach((k, v) -> System.out.println(v)); for map

            session.getTransaction().commit();
        }
    }

    @Test
    void checkManyToMany() {
        try (var factory = HibernateUtil.buildSessionFactory();
             var session = factory.openSession()) {

            session.beginTransaction();

            var user = session.get(User.class, 3L);
            var chat = session.get(Chat.class, 1L);

            /*var userChat = UserChat.builder()
                    .createdAt(Instant.now())
                    .createdBy(user.getUsername())
                    .build();

            userChat.setUser(user);
            userChat.setChat(chat);*/

//            user.addChat(chat);
//            session.save(userChat);

            session.getTransaction().commit();
        }
    }

    /*@Test
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
    }*/

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

    /*@Test
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
    }*/

    @Test
    void oneToMany() {
        @Cleanup var factory = HibernateUtil.buildSessionFactory();
        @Cleanup var session = factory.openSession();

        session.beginTransaction();

        var company = session.get(Company.class, 1);
        System.out.println(company);

        session.getTransaction().commit();
    }

    /*@Test
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
    }*/
}