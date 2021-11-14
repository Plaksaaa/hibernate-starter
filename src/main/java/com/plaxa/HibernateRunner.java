package com.plaxa;

import com.plaxa.entity.User;
import com.plaxa.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;

@Slf4j
public class HibernateRunner {

//    private static final Logger log = LoggerFactory.getLogger(HibernateRunner.class);

    public static void main(String[] args) throws SQLException {
        var user = User.builder()
                .username("loll")
                .lastname("ivan")
                .firstname("ivan")
                .build();

        log.info("User entity is in transient state, object : {}", user);

        try (var factory = HibernateUtil.buildSessionFactory()) {
            var session1 = factory.openSession();
            try (session1) {
                var transaction = session1.beginTransaction();
                log.trace("Transaction is created, {}", transaction);

                session1.saveOrUpdate(user);
                log.trace("User is in persistent state: {}, session {}", user, session1);


                session1.getTransaction().commit();
            }

            log.warn("User is in detached state: {}, session is closed {}", user, session1);
        } catch (Exception e) {
            log.error("Exception occurred", e);
            throw e;
        }

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
