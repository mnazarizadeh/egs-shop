package com.egs.shop.service;

import com.egs.shop.model.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    Page<ProductDTO> getEnabledProductsByCategoryId(Long id, Pageable pageable);

    Page<ProductDTO> getAllProductsByCategoryId(Long id, Pageable pageable);
}
