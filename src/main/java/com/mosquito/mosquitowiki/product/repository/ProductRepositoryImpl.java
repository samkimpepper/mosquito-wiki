package com.mosquito.mosquitowiki.product.repository;

import com.mosquito.mosquitowiki.product.domain.Product;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final EntityManager entityManager;

    @Override
    public List<Product> searchByName(String query) {
        String tsQuery = Arrays.stream(query.split("\\s+"))
                .collect(Collectors.joining(" & "));

        return entityManager.createNativeQuery("""
            SELECT * FROM products
            WHERE search_vector @@ to_tsquery('simple', :tsQuery)
               OR similarity(full_name, :query) > 0.2
               OR similarity(full_name_ko, :query) > 0.2
               OR full_name ILIKE :likeQuery
               OR full_name_ko ILIKE :likeQuery
            ORDER BY
                CASE WHEN search_vector @@ to_tsquery('simple', :tsQuery) THEN 1 ELSE 2 END,
                GREATEST(similarity(full_name, :query), similarity(full_name_ko, :query)) DESC
            LIMIT 10
            """, Product.class)
                .setParameter("tsQuery", tsQuery)
                .setParameter("query", query)
                .setParameter("likeQuery", "%" + query + "%")
                .getResultList();
    }
}
