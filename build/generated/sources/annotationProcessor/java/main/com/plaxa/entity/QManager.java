package com.plaxa.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QManager is a Querydsl query type for Manager
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QManager extends EntityPathBase<Manager> {

    private static final long serialVersionUID = 1510030639L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QManager manager = new QManager("manager");

    public final QUser _super;

    // inherited
    public final QCompany company;

    //inherited
    public final NumberPath<Long> id;

    //inherited
    public final StringPath info;

    //inherited
    public final ListPath<Payment, QPayment> payments;

    // inherited
    public final QPersonalInfo personalInfo;

    // inherited
    public final QProfile profile;

    public final StringPath projectName = createString("projectName");

    //inherited
    public final EnumPath<Role> role;

    //inherited
    public final ListPath<UserChat, QUserChat> userChats;

    //inherited
    public final StringPath username;

    public QManager(String variable) {
        this(Manager.class, forVariable(variable), INITS);
    }

    public QManager(Path<? extends Manager> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QManager(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QManager(PathMetadata metadata, PathInits inits) {
        this(Manager.class, metadata, inits);
    }

    public QManager(Class<? extends Manager> type, PathMetadata metadata, PathInits inits) {
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

