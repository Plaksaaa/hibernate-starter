package com.plaxa.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProgrammer is a Querydsl query type for Programmer
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QProgrammer extends EntityPathBase<Programmer> {

    private static final long serialVersionUID = 1667863572L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProgrammer programmer = new QProgrammer("programmer");

    public final QUser _super;

    // inherited
    public final QCompany company;

    //inherited
    public final NumberPath<Long> id;

    //inherited
    public final StringPath info;

    public final EnumPath<Language> language = createEnum("language", Language.class);

    //inherited
    public final ListPath<Payment, QPayment> payments;

    // inherited
    public final QPersonalInfo personalInfo;

    // inherited
    public final QProfile profile;

    //inherited
    public final EnumPath<Role> role;

    //inherited
    public final ListPath<UserChat, QUserChat> userChats;

    //inherited
    public final StringPath username;

    public QProgrammer(String variable) {
        this(Programmer.class, forVariable(variable), INITS);
    }

    public QProgrammer(Path<? extends Programmer> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProgrammer(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProgrammer(PathMetadata metadata, PathInits inits) {
        this(Programmer.class, metadata, inits);
    }

    public QProgrammer(Class<? extends Programmer> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QUser(type, metadata, inits);
        this.company = _super.company;
        this.id = _super.id;
        this.info = _super.info;
        this.payments = _super.payments;
        this.personalInfo = _super.personalInfo;
        this.profile = _super.profile;
        this.role = _super.role;
        this.userChats = _super.userChats;
        this.username = _super.username;
    }

}

