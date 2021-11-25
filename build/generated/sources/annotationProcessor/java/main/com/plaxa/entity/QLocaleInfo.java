package com.plaxa.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QLocaleInfo is a Querydsl query type for LocaleInfo
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QLocaleInfo extends BeanPath<LocaleInfo> {

    private static final long serialVersionUID = -1351748250L;

    public static final QLocaleInfo localeInfo = new QLocaleInfo("localeInfo");

    public final StringPath description = createString("description");

    public final StringPath lang = createString("lang");

    public QLocaleInfo(String variable) {
        super(LocaleInfo.class, forVariable(variable));
    }

    public QLocaleInfo(Path<? extends LocaleInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLocaleInfo(PathMetadata metadata) {
        super(LocaleInfo.class, metadata);
    }

}

