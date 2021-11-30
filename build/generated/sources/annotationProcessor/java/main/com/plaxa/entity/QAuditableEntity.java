package com.plaxa.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAuditableEntity is a Querydsl query type for AuditableEntity
 */
@Generated("com.querydsl.codegen.SupertypeSerializer")
public class QAuditableEntity extends EntityPathBase<AuditableEntity<? extends java.io.Serializable>> {

    private static final long serialVersionUID = 1401277306L;

    public static final QAuditableEntity auditableEntity = new QAuditableEntity("auditableEntity");

    public final DateTimePath<java.time.Instant> createdAt = createDateTime("createdAt", java.time.Instant.class);

    public final StringPath createdBy = createString("createdBy");

    public final DateTimePath<java.time.Instant> updatedAt = createDateTime("updatedAt", java.time.Instant.class);

    public final StringPath updatedBy = createString("updatedBy");

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QAuditableEntity(String variable) {
        super((Class) AuditableEntity.class, forVariable(variable));
    }

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QAuditableEntity(Path<? extends AuditableEntity> path) {
        super((Class) path.getType(), path.getMetadata());
    }

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QAuditableEntity(PathMetadata metadata) {
        super((Class) AuditableEntity.class, metadata);
    }

}

