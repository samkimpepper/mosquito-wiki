package com.mosquito.mosquitowiki.swatch;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QComboItem is a Querydsl query type for ComboItem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QComboItem extends EntityPathBase<ComboItem> {

    private static final long serialVersionUID = 2019188652L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QComboItem comboItem = new QComboItem("comboItem");

    public final QCombo combo;

    public final NumberPath<Integer> displayOrder = createNumber("displayOrder", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.mosquito.mosquitowiki.product.domain.QProduct product;

    public final StringPath role = createString("role");

    public QComboItem(String variable) {
        this(ComboItem.class, forVariable(variable), INITS);
    }

    public QComboItem(Path<? extends ComboItem> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QComboItem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QComboItem(PathMetadata metadata, PathInits inits) {
        this(ComboItem.class, metadata, inits);
    }

    public QComboItem(Class<? extends ComboItem> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.combo = inits.isInitialized("combo") ? new QCombo(forProperty("combo"), inits.get("combo")) : null;
        this.product = inits.isInitialized("product") ? new com.mosquito.mosquitowiki.product.domain.QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

