package com.egs.shop.service.impl;

import com.egs.shop.exception.ProductNotFoundException;
import com.egs.shop.model.dto.CommentDTO;
import com.egs.shop.model.mapper.CommentMapper;
import com.egs.shop.repository.CommentRepository;
import com.egs.shop.repository.ProductRepository;
import com.egs.shop.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final ProductRepository productRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    public Page<CommentDTO> getAllCommentsByProductId(Long id, Pageable pageable) {
        productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        return commentRepository.findByProductId(id, pageable)
                .map(commentMapper::toDto);
    }

    @Override
    public Page<CommentDTO> getEnabledCommentsByProductId(Long id, Pageable pageable) {
        productRepository.findByIdAndEnabledIsTrue(id)
                .orElseThrow(ProductNotFoundException::new);

        return commentRepository.findByProductIdAndProductEnabledIsTrue(id, pageable)
                .map(commentMapper::toDto);
    }
}
