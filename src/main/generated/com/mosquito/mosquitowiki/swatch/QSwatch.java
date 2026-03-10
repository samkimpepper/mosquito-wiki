package com.mosquito.mosquitowiki.swatch;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSwatch is a Querydsl query type for Swatch
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSwatch extends EntityPathBase<Swatch> {

    private static final long serialVersionUID = -564191695L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSwatch swatch = new QSwatch("swatch");

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    public final com.mosquito.mosquitowiki.product.domain.QProduct product;

    public final EnumPath<SourceType> sourceType = createEnum("sourceType", SourceType.class);

    public final StringPath tweetUrl = createString("tweetUrl");

    public final com.mosquito.mosquitowiki.users.QUser user;

    public final NumberPath<Integer> viewCount = createNumber("viewCount", Integer.class);

    public QSwatch(String variable) {
        this(Swatch.class, forVariable(variable), INITS);
    }

    public QSwatch(Path<? extends Swatch> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSwatch(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSwatch(PathMetadata metadata, PathInits inits) {
        this(Swatch.class, metadata, inits);
    }

    public QSwatch(Class<? extends Swatch> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new com.mosquito.mosquitowiki.product.domain.QProduct(forProperty("product"), inits.get("product")) : null;
        this.user = inits.isInitialized("user") ? new com.mosquito.mosquitowiki.users.QUser(forProperty("user")) : null;
    }

}

