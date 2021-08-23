package com.egs.shop.repository;

import com.egs.shop.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Page<Category> findAllByEnabledIsTrue(Pageable pageable);

    Optional<Category> findByIdAndEnabledIsTrue(Long id);

    Optional<Category> findByTitle(String title);
}
