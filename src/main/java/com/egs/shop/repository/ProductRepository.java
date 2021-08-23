package com.egs.shop.repository;

import com.egs.shop.model.Category;
import com.egs.shop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    long countByCategory(Category category);

}
