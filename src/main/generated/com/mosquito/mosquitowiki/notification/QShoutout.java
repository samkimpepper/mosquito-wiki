package com.mosquito.mosquitowiki.notification;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QShoutout is a Querydsl query type for Shoutout
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QShoutout extends EntityPathBase<Shoutout> {

    private static final long serialVersionUID = -205854085L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QShoutout shoutout = new QShoutout("shoutout");

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> expiresAt = createDateTime("expiresAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isActive = createBoolean("isActive");

    public final StringPath linkUrl = createString("linkUrl");

    public final com.mosquito.mosquitowiki.product.domain.QProduct product;

    public final com.mosquito.mosquitowiki.users.QUser user;

    public QShoutout(String variable) {
        this(Shoutout.class, forVariable(variable), INITS);
    }

    public QShoutout(Path<? extends Shoutout> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QShoutout(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QShoutout(PathMetadata metadata, PathInits inits) {
        this(Shoutout.class, metadata, inits);
    }

    public QShoutout(Class<? extends Shoutout> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new com.mosquito.mosquitowiki.product.domain.QProduct(forProperty("product"), inits.get("product")) : null;
        this.user = inits.isInitialized("user") ? new com.mosquito.mosquitowiki.users.QUser(forProperty("user")) : null;
    }

}

