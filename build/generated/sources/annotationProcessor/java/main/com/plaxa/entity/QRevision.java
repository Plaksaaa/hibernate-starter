package com.plaxa.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QRevision is a Querydsl query type for Revision
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRevision extends EntityPathBase<Revision> {

    private static final long serialVersionUID = -817743207L;

    public static final QRevision revision = new QRevision("revision");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> timestamp = createNumber("timestamp", Long.class);

    public final StringPath username = createString("username");

    public QRevision(String variable) {
        super(Revision.class, forVariable(variable));
    }

    public QRevision(Path<? extends Revision> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRevision(PathMetadata metadata) {
        super(Revision.class, metadata);
    }

}

