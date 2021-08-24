package com.egs.shop.service;

import com.egs.shop.model.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    ProductDTO createProduct(ProductDTO productDTO);

    ProductDTO updateProduct(ProductDTO productDTO);

    Page<ProductDTO> getAllProducts(Pageable pageable);

    Page<ProductDTO> getEnabledProducts(Pageable pageable);

    ProductDTO getProductById(Long id);

    ProductDTO getEnabledProductById(Long id);

    void deleteById(Long id);

    Page<ProductDTO> getEnabledProductsByCategoryId(Long id, Pageable pageable);

    Page<ProductDTO> getAllProductsByCategoryId(Long id, Pageable pageable);

}
