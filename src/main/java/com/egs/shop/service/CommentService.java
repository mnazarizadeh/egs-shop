package com.egs.shop.service;

import com.egs.shop.model.dto.CommentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {

    CommentDTO createComment(CommentDTO commentDTO);

    CommentDTO updateComment(CommentDTO commentDTO);

    Page<CommentDTO> getAllComments(Pageable pageable);

    Page<CommentDTO> getMyComments(Pageable pageable);

    CommentDTO getComment(Long id);

    CommentDTO getMyComment(Long id);

    void deleteById(Long id);

    void deleteMineById(Long id);

    Page<CommentDTO> getAllCommentsByProductId(Long id, Pageable pageable);

    Page<CommentDTO> getEnabledCommentsByProductId(Long id, Pageable pageable);

}
