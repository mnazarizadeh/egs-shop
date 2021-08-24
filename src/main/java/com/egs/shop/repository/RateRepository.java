package com.egs.shop.repository;

import com.egs.shop.model.Product;
import com.egs.shop.model.Rate;
import com.egs.shop.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {

    long countByProduct(Product product);

    List<Rate> findAllByProductId(Long productId);

    Page<Rate> findByProductId(Long id, Pageable pageable);

    Page<Rate> findByProductIdAndProductEnabledIsTrue(Long id, Pageable pageable);

    Optional<Rate> findByUserAndProduct(User user, Product product);

    Page<Rate> findByUserUsername(String authenticatedUsername, Pageable pageable);
}
