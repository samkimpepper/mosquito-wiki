package com.mosquito.mosquitowiki.swatch;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSwatchTag is a Querydsl query type for SwatchTag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSwatchTag extends EntityPathBase<SwatchTag> {

    private static final long serialVersionUID = -1627672663L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSwatchTag swatchTag = new QSwatchTag("swatchTag");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QSwatch swatch;

    public final StringPath tagType = createString("tagType");

    public final StringPath tagValue = createString("tagValue");

    public QSwatchTag(String variable) {
        this(SwatchTag.class, forVariable(variable), INITS);
    }

    public QSwatchTag(Path<? extends SwatchTag> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSwatchTag(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSwatchTag(PathMetadata metadata, PathInits inits) {
        this(SwatchTag.class, metadata, inits);
    }

    public QSwatchTag(Class<? extends SwatchTag> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.swatch = inits.isInitialized("swatch") ? new QSwatch(forProperty("swatch"), inits.get("swatch")) : null;
    }

}

