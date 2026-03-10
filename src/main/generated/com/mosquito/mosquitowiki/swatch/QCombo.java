package com.mosquito.mosquitowiki.swatch;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCombo is a Querydsl query type for Combo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCombo extends EntityPathBase<Combo> {

    private static final long serialVersionUID = -1695771399L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCombo combo = new QCombo("combo");

    public final EnumPath<ComboType> comboType = createEnum("comboType", ComboType.class);

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    public final EnumPath<SourceType> sourceType = createEnum("sourceType", SourceType.class);

    public final StringPath title = createString("title");

    public final StringPath tweetUrl = createString("tweetUrl");

    public final com.mosquito.mosquitowiki.users.QUser user;

    public final NumberPath<Integer> viewCount = createNumber("viewCount", Integer.class);

    public QCombo(String variable) {
        this(Combo.class, forVariable(variable), INITS);
    }

    public QCombo(Path<? extends Combo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCombo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCombo(PathMetadata metadata, PathInits inits) {
        this(Combo.class, metadata, inits);
    }

    public QCombo(Class<? extends Combo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.mosquito.mosquitowiki.users.QUser(forProperty("user")) : null;
    }

}

