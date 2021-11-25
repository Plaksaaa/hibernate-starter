package com.plaxa.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -1656783479L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final QCompany company;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath info = createString("info");

    public final ListPath<Payment, QPayment> payments = this.<Payment, QPayment>createList("payments", Payment.class, QPayment.class, PathInits.DIRECT2);

    public final QPersonalInfo personalInfo;

    public final QProfile profile;

    public final EnumPath<Role> role = createEnum("role", Role.class);

    public final ListPath<UserChat, QUserChat> userChats = this.<UserChat, QUserChat>createList("userChats", UserChat.class, QUserChat.class, PathInits.DIRECT2);

    public final StringPath username = createString("username");

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Path<? extends User> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUser(PathMetadata metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.company = inits.isInitialized("company") ? new QCompany(forProperty("company")) : null;
        this.personalInfo = inits.isInitialized("personalInfo") ? new QPersonalInfo(forProperty("personalInfo")) : null;
        this.profile = inits.isInitialized("profile") ? new QProfile(forProperty("profile"), inits.get("profile")) : null;
    }

}

