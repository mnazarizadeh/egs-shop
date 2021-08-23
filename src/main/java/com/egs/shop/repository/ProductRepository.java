package com.egs.shop.repository;

import com.egs.shop.model.Category;
import com.egs.shop.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    long countByCategory(Category category);

    Page<Product> findByEnabledIsTrueAndCategoryIdAndCategoryEnabledIsTrue(Long id, Pageable pageable);

    Page<Product> findByCategoryId(Long id, Pageable pageable);
}
