package com.mosquito.mosquitowiki.product.repository;

import com.mosquito.mosquitowiki.product.domain.Product;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final EntityManager entityManager;

    @Override
    public List<Product> searchByName(String query) {
        return entityManager.createNativeQuery("""
                SELECT * FROM products
                WHERE similarity(name, :query) > 0.2
                   OR similarity(name_ko, :query) > 0.2
                   OR name ILIKE :likeQuery
                   OR name_ko ILIKE :likeQuery
                ORDER BY
                    GREATEST(similarity(name, :query), similarity(name_ko, :query)) DESC
                LIMIT 10
                """, Product.class)
                .setParameter("query", query)
                .setParameter("likeQuery", "%" + query + "%")
                .getResultList();
    }
}
