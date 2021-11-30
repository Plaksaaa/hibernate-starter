package com.plaxa.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAudit is a Querydsl query type for Audit
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAudit extends EntityPathBase<Audit> {

    private static final long serialVersionUID = 160907741L;

    public static final QAudit audit = new QAudit("audit");

    public final StringPath entityContent = createString("entityContent");

    public final SimplePath<java.io.Serializable> entityId = createSimple("entityId", java.io.Serializable.class);

    public final StringPath entityName = createString("entityName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<Audit.Operation> operation = createEnum("operation", Audit.Operation.class);

    public QAudit(String variable) {
        super(Audit.class, forVariable(variable));
    }

    public QAudit(Path<? extends Audit> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAudit(PathMetadata metadata) {
        super(Audit.class, metadata);
    }

}

