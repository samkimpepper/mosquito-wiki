package com.mosquito.mosquitowiki.product.repository;

import com.mosquito.mosquitowiki.product.domain.Product;
import com.mosquito.mosquitowiki.product.domain.ProductLike;
import com.mosquito.mosquitowiki.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductLikeRepository extends JpaRepository<ProductLike, Long> {

    boolean existsByProductAndUser(Product product, User user);

    void deleteByProductAndUser(Product product, User user);

    long countByProduct(Product product);
}
