package com.mosquito.mosquitowiki.product.repository;

import com.mosquito.mosquitowiki.home.CategoryStatResponse;
import com.mosquito.mosquitowiki.product.domain.Brand;
import com.mosquito.mosquitowiki.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {

    Optional<Product> findBySlug(String slug);

    List<Product> findBySlugIn(List<String> slugs);

    boolean existsBySlug(String slug);

    List<Product> findAllByBrand(Brand brand);

    List<Product> findAllByParent(Product parent);

    // ProductRepository
    @Query("SELECT new com.mosquito.mosquitowiki.home.CategoryStatResponse(c.name, COUNT(p)) " +
            "FROM Product p JOIN p.category c " +
            "GROUP BY c.name")
    List<CategoryStatResponse> countByCategory();
}
