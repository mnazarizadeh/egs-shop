package com.egs.shop.repository;

import com.egs.shop.model.Comment;
import com.egs.shop.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    long countByProduct(Product product);

    Page<Comment> findByProductId(Long id, Pageable pageable);

    Page<Comment> findByProductIdAndProductEnabledIsTrue(Long id, Pageable pageable);

}
