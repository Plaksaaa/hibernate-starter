package com.plaxa.entity;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.FetchProfile;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static com.plaxa.util.StringUtils.SPACE;

@NamedEntityGraph(name = "withCompanyAndChat",
        attributeNodes = {
                @NamedAttributeNode("company"),
                @NamedAttributeNode(value = "userChats", subgraph = "chats")
        },
        subgraphs = {
                @NamedSubgraph(name = "chats", attributeNodes = @NamedAttributeNode("chat"))
        }
)
@FetchProfile(name = "withCompanyAndPayments", fetchOverrides = {
        @FetchProfile.FetchOverride(entity = User.class, association = "company", mode = FetchMode.JOIN
        ),
        @FetchProfile.FetchOverride(entity = User.class, association = "payments", mode = FetchMode.JOIN
        )
})
@Data
@NoArgsConstructor
@AllArgsConstructor
// тоже должен быть not null чтобы сравнивать
@EqualsAndHashCode(of = "username")
@ToString(exclude = {"company", "profile", "userChats", "payments"})
@Builder
@Entity
@Table(name = "users", schema = "public")
//@Inheritance(strategy = InheritanceType.JOINED)
public class User implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_gen")
//    @SequenceGenerator(name = "user_gen", sequenceName = "users_id_seq", allocationSize = 1)
    private Long id;

    @Column(unique = true)
    private String username;

    @Embedded
    @AttributeOverride(name = "birthDate", column = @Column(name = "birth_date"))
    private PersonalInfo personalInfo;

    @Type(type = "jsonb")
    private String info;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "company_id")
//    @Fetch(FetchMode.JOIN)
    private Company company;

    @OneToOne(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
//            optional = false
    )
    private Profile profile;

    @Fetch(FetchMode.SUBSELECT)
    @Builder.Default
    @OneToMany(mappedBy = "receiver")
    private List<Payment> payments = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<UserChat> userChats = new ArrayList<>();

    public String fullName() {
        return getPersonalInfo().getFirstname() + SPACE + getPersonalInfo().getLastname();
    }

    /*@ManyToMany
    @JoinTable(
            name = "users_chat",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "chat_id")
    )
    @Builder.Default
    private List<Chat> chats = new ArrayList<>();

    public void addChat(Chat chat) {
        chats.add(chat);
//        chat.getUsers().add(this);
    }*/
}
