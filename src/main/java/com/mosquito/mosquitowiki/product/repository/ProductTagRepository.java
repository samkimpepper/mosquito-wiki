package com.mosquito.mosquitowiki.product.repository;

import com.mosquito.mosquitowiki.product.domain.Product;
import com.mosquito.mosquitowiki.product.domain.ProductTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductTagRepository extends JpaRepository<ProductTag, Long> {

    List<ProductTag> findByProductId(Long productId);

    void deleteByProductAndTagIdIn(Product product, List<Long> tagIds);
}
