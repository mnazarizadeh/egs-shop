package com.egs.shop.web.rest;

import com.egs.shop.model.dto.CommentDTO;
import com.egs.shop.service.CommentService;
import com.egs.shop.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class CommentResource {

    private final CommentService commentService;

    @PostMapping("/comments")
    public ResponseEntity<CommentDTO> createComment(@Valid @RequestBody CommentDTO comment) throws URISyntaxException {
        log.debug("REST request to make a comment on a product : {}", comment);

        CommentDTO result = commentService.createComment(comment);
        return ResponseEntity
                .created(new URI("/api/comments/" + result.getId()))
                .body(result);
    }

    @PutMapping("/comments/{id}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable(value = "id") final Long id,
                                                    @Valid @RequestBody CommentDTO comment) throws URISyntaxException {
        log.debug("REST request to update Comment : {}, {}", id, comment);

        comment.setId(id);
        CommentDTO result = commentService.updateComment(comment);
        return ResponseEntity
                .ok()
                .body(result);
    }

    @GetMapping("/admin/comments")
    public ResponseEntity<List<CommentDTO>> getAllComments(Pageable pageable) {
        log.debug("REST request to get a page of Comments");

        Page<CommentDTO> page = commentService.getAllComments(pageable);
        return ResponseEntity.ok()
                .headers(PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page))
                .body(page.getContent());
    }

    @GetMapping("/comments")
    public ResponseEntity<List<CommentDTO>> getMyComments(Pageable pageable) {
        log.debug("REST request to get a page of user Comments");

        Page<CommentDTO> page = commentService.getMyComments(pageable);
        return ResponseEntity.ok()
                .headers(PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page))
                .body(page.getContent());
    }

    @GetMapping("/admin/comments/{id}")
    public ResponseEntity<CommentDTO> getComment(@PathVariable Long id) {
        log.debug("REST request to get Comments {} by ADMIN", id);

        CommentDTO result = commentService.getComment(id);
        return ResponseEntity.ok()
                .body(result);
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<CommentDTO> getMyComment(@PathVariable Long id) {
        log.debug("REST request to get Comments {} by USER", id);

        CommentDTO result = commentService.getMyComment(id);
        return ResponseEntity.ok()
                .body(result);
    }

    @DeleteMapping("/admin/comments/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        log.debug("REST request to delete Comment {} by ADMIN", id);

        commentService.deleteById(id);
        return ResponseEntity
                .noContent()
                .build();
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Void> deleteMyComment(@PathVariable Long id) {
        log.debug("REST request to delete Comment {} by USER", id);

        commentService.deleteMineById(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
