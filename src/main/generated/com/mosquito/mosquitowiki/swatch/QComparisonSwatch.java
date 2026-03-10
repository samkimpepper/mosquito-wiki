package com.mosquito.mosquitowiki.swatch;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QComparisonSwatch is a Querydsl query type for ComparisonSwatch
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QComparisonSwatch extends EntityPathBase<ComparisonSwatch> {

    private static final long serialVersionUID = -586293926L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QComparisonSwatch comparisonSwatch = new QComparisonSwatch("comparisonSwatch");

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

    public QComparisonSwatch(String variable) {
        this(ComparisonSwatch.class, forVariable(variable), INITS);
    }

    public QComparisonSwatch(Path<? extends ComparisonSwatch> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QComparisonSwatch(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QComparisonSwatch(PathMetadata metadata, PathInits inits) {
        this(ComparisonSwatch.class, metadata, inits);
    }

    public QComparisonSwatch(Class<? extends ComparisonSwatch> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.mosquito.mosquitowiki.users.QUser(forProperty("user")) : null;
    }

}

