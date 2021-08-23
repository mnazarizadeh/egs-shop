package com.egs.shop.web.rest;

import com.egs.shop.model.dto.CategoryDTO;
import com.egs.shop.service.CategoryService;
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
public class CategoryResource {

    private final CategoryService categoryService;

    /**
     * {@code POST  /admin/categories} : Create a new operation.
     *
     * @param category the category to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new operation, or with status {@code 400 (Bad Request)} if operation failed.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/admin/categories")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO category) throws URISyntaxException {
        log.debug("REST request to save Category : {}", category);

        CategoryDTO result = categoryService.createCategory(category);
        return ResponseEntity
                .created(new URI("/api/categories/" + result.getId()))
                .body(result);
    }

    /**
     * {@code PUT  /admin/categories/:id} : Updates an existing category.
     *
     * @param id the id of the category to save.
     * @param category the category to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated category,
     * or with status {@code 400 (Bad Request)} if the category is not valid,
     * or with status {@code 500 (Internal Server Error)} if the category couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/admin/categories/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable(value = "id") final Long id,
                                                       @Valid @RequestBody CategoryDTO category) throws URISyntaxException {
        log.debug("REST request to update Operation : {}, {}", id, category);

        category.setId(id);
        CategoryDTO result = categoryService.updateCategory(category);
        return ResponseEntity
                .ok()
                .body(result);
    }

    /**
     * {@code GET  /admin/categories} : get all the categories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categories in body.
     */
    @GetMapping("/admin/categories")
    public ResponseEntity<List<CategoryDTO>> getAllCategories(Pageable pageable) {
        log.debug("REST request to get a page of Categories");

        Page<CategoryDTO> page = categoryService.getAllCategories(pageable);
        return ResponseEntity.ok()
                .headers(PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page))
                .body(page.getContent());
    }

    /**
     * {@code GET  /categories} : get enabled categories for user.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categories in body.
     */
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> getEnabledCategories(Pageable pageable) {
        log.debug("REST request to get a page of Categories");

        Page<CategoryDTO> page = categoryService.getEnabledCategories(pageable);
        return ResponseEntity.ok()
                .headers(PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page))
                .body(page.getContent());
    }

    /**
     * {@code GET  /admin/categories/:id} : get the "id" category for admin.
     *
     * @param id the id of the category to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the category, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/admin/categories/{id}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable Long id) {
        log.debug("REST request to get Category : {}", id);

        CategoryDTO category = categoryService.getCategoryById(id);
        return ResponseEntity.ok()
                .body(category);
    }

    /**
     * {@code GET  /categories/:id} : get the "id" category.
     *
     * @param id the id of the category to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the category, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryDTO> getEnabledCategory(@PathVariable Long id) {
        log.debug("REST request to get enabled Category : {}", id);

        CategoryDTO category = categoryService.getEnabledCategoryById(id);
        return ResponseEntity.ok()
                .body(category);
    }

    /**
     * {@code DELETE  /admin/operations/:id} : delete the "id" operation.
     *
     * @param id the id of the operation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/admin/categories/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        log.debug("REST request to delete Category : {}", id);

        categoryService.deleteById(id);
        return ResponseEntity
                .noContent()
                .build();
    }

}
