package com.egs.shop.service.impl;

import com.egs.shop.exception.*;
import com.egs.shop.model.Comment;
import com.egs.shop.model.Product;
import com.egs.shop.model.User;
import com.egs.shop.model.dto.CommentDTO;
import com.egs.shop.model.mapper.CommentMapper;
import com.egs.shop.repository.CommentRepository;
import com.egs.shop.repository.ProductRepository;
import com.egs.shop.repository.UserRepository;
import com.egs.shop.security.SecurityUtils;
import com.egs.shop.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    public CommentDTO createComment(CommentDTO commentDTO) {
        Product product = productRepository.findById(commentDTO.getProductId())
                .orElseThrow(ProductNotFoundException::new);
        User user = userRepository.findByUsername(
                SecurityUtils.getCurrentUserUsername()
                        .orElseThrow(AuthenticatedUserNotFoundException::new))
                .orElseThrow(UserNotFoundException::new);

        // check for previous comments for this item
        commentRepository.findByUserAndProduct(user,product)
                .ifPresent(comment -> {
                    throw new CommentAlreadyExistsException();
                });

        Comment newComment = commentMapper.toEntity(commentDTO);
        newComment.setProduct(product);
        newComment.setUser(user);
        newComment.setCreateDate(LocalDateTime.now());

        return commentMapper.toDto(commentRepository.save(newComment));
    }

    @Override
    public CommentDTO updateComment(CommentDTO commentDTO) {
        Comment comment = commentRepository.findById(commentDTO.getId())
                .orElseThrow(CommentNotFoundException::new);

        checkUserAccess(comment);

        comment.setMessage(commentDTO.getMessage());
        return commentMapper.toDto(commentRepository.save(comment));
    }

    @Override
    public Page<CommentDTO> getAllComments(Pageable pageable) {
        return commentRepository.findAll(pageable)
                .map(commentMapper::toDto);
    }

    @Override
    public Page<CommentDTO> getMyComments(Pageable pageable) {
        String authenticatedUsername = SecurityUtils.getCurrentUserUsername()
                .orElseThrow(AuthenticatedUserNotFoundException::new);

        return commentRepository.findByUserUsername(authenticatedUsername, pageable)
                .map(commentMapper::toDto);
    }

    @Override
    public CommentDTO getComment(Long id) {
        return commentRepository.findById(id)
                .map(commentMapper::toDto)
                .orElseThrow(CommentNotFoundException::new);
    }

    @Override
    public CommentDTO getMyComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(CommentNotFoundException::new);

        checkUserAccess(comment);

        return commentMapper.toDto(comment);
    }

    @Override
    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public void deleteMineById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(CommentNotFoundException::new);
        checkUserAccess(comment);
        this.deleteById(id);
    }

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

    private void checkUserAccess(Comment comment) {
        if (!comment.getUser().getUsername().equals(
                SecurityUtils.getCurrentUserUsername()
                        .orElseThrow(AuthenticatedUserNotFoundException::new))) {
            throw new CommentAccessDeniedException();
        }
    }
}
