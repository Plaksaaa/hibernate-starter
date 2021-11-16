package com.plaxa;

import com.plaxa.entity.Birthday;
import com.plaxa.entity.Company;
import com.plaxa.entity.PersonalInfo;
import com.plaxa.entity.User;
import com.plaxa.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.time.LocalDate;

@Slf4j
public class HibernateRunner {

//    private static final Logger log = LoggerFactory.getLogger(HibernateRunner.class);

    public static void main(String[] args) throws SQLException {
        var company = Company.builder()
                .name("Google")
                .build();

        var user = User.builder()
                .username("nasty@lol")
                .personalInfo(PersonalInfo.builder()
                        .firstname("nasty")
                        .lastname("redHead")
                        .birthDate(new Birthday(LocalDate.of(2004, 4, 27)))
                        .build())
                .company(company)
                .build();

        log.info("User entity is in transient state, object : {}", user);

        try (var factory = HibernateUtil.buildSessionFactory()) {
            var session1 = factory.openSession();
            try (session1) {

                var transaction = session1.beginTransaction();

                var user1 = session1.get(User.class, 1L);
                System.out.println(user1);
                /*session1.save(company);
                session1.save(user);*/

                session1.getTransaction().commit();
            }

            /*log.warn("User is in detached state: {}, session is closed {}", user, session1);

            try (Session session = factory.openSession()) {
                var key = PersonalInfo.builder()
                        .firstname("nasty")
                        .lastname("redHead")
                        .birthDate(new Birthday(LocalDate.of(2004, 4, 27)))
                        .build();

                var user1 = session.get(User.class, key);
                System.out.println(user1);
            }
        } catch (Exception e) {
            log.error("Exception occurred", e);
            throw e;
        }*/

           /* try (var session2 = factory.openSession()) {
                session2.beginTransaction();


                session2.getTransaction().commit();
            }
        }*/
        /*try (var factory = configuration.buildSessionFactory();
             var session = factory.openSession()) {
            session.beginTransaction();

            User user = User.builder()
                    .username("max@gmail.com")
                    .firstname("Max")
                    .lastname("Plaxa")
                    .info("""
                            {
                                "name" : "Max",
                                "id" : 25
                            }
                            """)
                    .birthDate(new Birthday(LocalDate.of(2003, 3, 12)))
                    .role(Role.ADMIN)
                    .build();

            session.save(user);

            session.getTransaction().commit();
        }*/
        }
    }
}
