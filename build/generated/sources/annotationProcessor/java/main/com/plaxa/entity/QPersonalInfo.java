package com.plaxa.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QPersonalInfo is a Querydsl query type for PersonalInfo
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QPersonalInfo extends BeanPath<PersonalInfo> {

    private static final long serialVersionUID = -750660916L;

    public static final QPersonalInfo personalInfo = new QPersonalInfo("personalInfo");

    public final DatePath<java.time.LocalDate> birthDate = createDate("birthDate", java.time.LocalDate.class);

    public final StringPath firstname = createString("firstname");

    public final StringPath lastname = createString("lastname");

    public QPersonalInfo(String variable) {
        super(PersonalInfo.class, forVariable(variable));
    }

    public QPersonalInfo(Path<? extends PersonalInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPersonalInfo(PathMetadata metadata) {
        super(PersonalInfo.class, metadata);
    }

}

