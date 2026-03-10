package com.mosquito.mosquitowiki.swatch;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QComparisonLink is a Querydsl query type for ComparisonLink
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QComparisonLink extends EntityPathBase<ComparisonLink> {

    private static final long serialVersionUID = -255580008L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QComparisonLink comparisonLink = new QComparisonLink("comparisonLink");

    public final QComparisonSwatch comparisonSwatch;

    public final NumberPath<Integer> displayOrder = createNumber("displayOrder", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.mosquito.mosquitowiki.product.domain.QProduct product;

    public QComparisonLink(String variable) {
        this(ComparisonLink.class, forVariable(variable), INITS);
    }

    public QComparisonLink(Path<? extends ComparisonLink> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QComparisonLink(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QComparisonLink(PathMetadata metadata, PathInits inits) {
        this(ComparisonLink.class, metadata, inits);
    }

    public QComparisonLink(Class<? extends ComparisonLink> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.comparisonSwatch = inits.isInitialized("comparisonSwatch") ? new QComparisonSwatch(forProperty("comparisonSwatch"), inits.get("comparisonSwatch")) : null;
        this.product = inits.isInitialized("product") ? new com.mosquito.mosquitowiki.product.domain.QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

