package com.mosquito.mosquitowiki.product.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductFollow is a Querydsl query type for ProductFollow
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductFollow extends EntityPathBase<ProductFollow> {

    private static final long serialVersionUID = -1776009454L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductFollow productFollow = new QProductFollow("productFollow");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QProduct product;

    public final com.mosquito.mosquitowiki.users.QUser user;

    public QProductFollow(String variable) {
        this(ProductFollow.class, forVariable(variable), INITS);
    }

    public QProductFollow(Path<? extends ProductFollow> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductFollow(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductFollow(PathMetadata metadata, PathInits inits) {
        this(ProductFollow.class, metadata, inits);
    }

    public QProductFollow(Class<? extends ProductFollow> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product"), inits.get("product")) : null;
        this.user = inits.isInitialized("user") ? new com.mosquito.mosquitowiki.users.QUser(forProperty("user")) : null;
    }

}

