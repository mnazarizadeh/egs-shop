package com.egs.shop.service;

import com.egs.shop.model.dto.CommentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {

    Page<CommentDTO> getAllCommentsByProductId(Long id, Pageable pageable);

    Page<CommentDTO> getEnabledCommentsByProductId(Long id, Pageable pageable);

}
