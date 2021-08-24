package com.egs.shop.repository;

import com.egs.shop.model.Rate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {

    List<Rate> findAllByProductId(Long productId);

    Page<Rate> findByProductId(Long id, Pageable pageable);

    Page<Rate> findByProductIdAndProductEnabledIsTrue(Long id, Pageable pageable);

}
