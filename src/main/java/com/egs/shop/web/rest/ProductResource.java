package com.egs.shop.web.rest;

import com.egs.shop.model.dto.CommentDTO;
import com.egs.shop.model.dto.ProductDTO;
import com.egs.shop.model.dto.RateDTO;
import com.egs.shop.service.CommentService;
import com.egs.shop.service.ProductService;
import com.egs.shop.service.RateService;
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
public class ProductResource {

    private final ProductService productService;
    private final CommentService commentService;
    private final RateService rateService;

    /**
     * {@code POST  /admin/products} : Create a new product.
     *
     * @param product the product to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new product, or with status {@code 400 (Bad Request)} if product failed.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/admin/products")
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO product) throws URISyntaxException {
        log.debug("REST request to save Product : {}", product);

        ProductDTO result = productService.createProduct(product);
        return ResponseEntity
                .created(new URI("/api/admin/products/" + result.getId()))
                .body(result);
    }

    /**
     * {@code PUT  /admin/products/:id} : Updates an existing product.
     *
     * @param id the id of the product to save.
     * @param products the product to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated product,
     * or with status {@code 400 (Bad Request)} if the product is not valid,
     * or with status {@code 500 (Internal Server Error)} if the product couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/admin/products/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable(value = "id") final Long id,
                                                    @Valid @RequestBody ProductDTO product) throws URISyntaxException {
        log.debug("REST request to update Product : {}, {}", id, product);

        product.setId(id);
        ProductDTO result = productService.updateProduct(product);
        return ResponseEntity
                .ok()
                .body(result);
    }

    /**
     * {@code GET  /admin/products} : get all the products.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of products in body.
     */
    @GetMapping("/admin/products")
    public ResponseEntity<List<ProductDTO>> getAllProducts(Pageable pageable) {
        log.debug("REST request to get a page of Products");

        Page<ProductDTO> page = productService.getAllProducts(pageable);
        return ResponseEntity.ok()
                .headers(PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page))
                .body(page.getContent());
    }

    /**
     * {@code GET  /products} : get enabled products for user.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of products in body.
     */
    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getEnabledProducts(Pageable pageable) {
        log.debug("REST request to get a page of active Products");

        Page<ProductDTO> page = productService.getEnabledProducts(pageable);
        return ResponseEntity.ok()
                .headers(PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page))
                .body(page.getContent());
    }

    /**
     * {@code GET  /admin/products/:id} : get the "id" product for admin.
     *
     * @param id the id of the product to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the product, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/admin/products/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) {
        log.debug("REST request to get Product : {}", id);

        ProductDTO product = productService.getProductById(id);
        return ResponseEntity.ok()
                .body(product);
    }

    /**
     * {@code GET  /admin/products/:id/comments} : get the "id" product comments list.
     *
     * @param id the id of the product to retrieve its comments.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the comments, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/admin/products/{id}/comments")
    public ResponseEntity<List<CommentDTO>> getAllProductComments(@PathVariable Long id, Pageable pageable) {
        log.debug("REST request to get the comments of a Product : {}", id);

        Page<CommentDTO> page = commentService.getAllCommentsByProductId(id, pageable);
        return ResponseEntity.ok()
                .headers(PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page))
                .body(page.getContent());
    }

    /**
     * {@code GET  /admin/products/:id/rates} : get the "id" product rates list.
     *
     * @param id the id of the product to retrieve its rates.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rates, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/admin/products/{id}/rates")
    public ResponseEntity<List<RateDTO>> getAllProductRates(@PathVariable Long id, Pageable pageable) {
        log.debug("REST request to get the rates of a Product : {}", id);

        Page<RateDTO> page = rateService.getAllRatesByProductId(id, pageable);
        return ResponseEntity.ok()
                .headers(PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page))
                .body(page.getContent());
    }

    /**
     * {@code GET  /products/:id} : get the "id" product for user.
     *
     * @param id the id of the product to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the product, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDTO> getEnabledProducts(@PathVariable Long id) {
        log.debug("REST request to get enabled Product : {}", id);

        ProductDTO product = productService.getEnabledProductById(id);
        return ResponseEntity.ok()
                .body(product);
    }

    /**
     * {@code GET  /products/:id/comments} : get the "id" product comments list.
     *
     * @param id the id of the product to retrieve its comments.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the comments, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/products/{id}/comments")
    public ResponseEntity<List<CommentDTO>> getEnabledProductComments(@PathVariable Long id, Pageable pageable) {
        log.debug("REST request to get the comments of an enabled Product : {}", id);

        Page<CommentDTO> page = commentService.getEnabledCommentsByProductId(id, pageable);
        return ResponseEntity.ok()
                .headers(PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page))
                .body(page.getContent());
    }

    /**
     * {@code GET  /products/:id/rates} : get the "id" product rates list.
     *
     * @param id the id of the product to retrieve its rates.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rates, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/products/{id}/rates")
    public ResponseEntity<List<RateDTO>> getEnabledProductRates(@PathVariable Long id, Pageable pageable) {
        log.debug("REST request to get the rates of an enabled Product : {}", id);

        Page<RateDTO> page = rateService.getEnabledRatesByProductId(id, pageable);
        return ResponseEntity.ok()
                .headers(PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page))
                .body(page.getContent());
    }

    /**
     * {@code DELETE  /admin/products/:id} : delete the "id" product.
     *
     * @param id the id of the product to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/admin/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        log.debug("REST request to delete Product : {}", id);

        productService.deleteById(id);
        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping("/products/search")
    public ResponseEntity<List<ProductDTO>> search(@RequestParam(value = "search") String search) {
        log.debug("REST request to search Products : {}", search);

        return ResponseEntity.ok()
                .body(productService.search(search));
    }

}
