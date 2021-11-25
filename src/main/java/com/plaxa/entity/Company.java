package com.plaxa.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "users")
@EqualsAndHashCode(of = "name")
@Builder
@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("username DESC , personalInfo.lastname ASC")
//    @JoinColumn(name = "company_id") если нет manyToOne в user
    private List<User> users = new ArrayList<>();

    @ElementCollection
    @Builder.Default
    @CollectionTable(name = "company_locale", joinColumns = @JoinColumn(name = "company_id"))
    @Column(name = "description")
    @MapKeyColumn(name = "lang")
    private Map<String, String> locales = new HashMap<>();

    public void addUser(User user) {
        users.add(user);
        user.setCompany(this);
    }
}
