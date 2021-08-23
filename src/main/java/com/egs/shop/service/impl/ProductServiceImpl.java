package com.egs.shop.service.impl;

import com.egs.shop.model.Rate;
import com.egs.shop.model.dto.ProductDTO;
import com.egs.shop.model.mapper.ProductMapper;
import com.egs.shop.repository.CommentRepository;
import com.egs.shop.repository.ProductRepository;
import com.egs.shop.repository.RateRepository;
import com.egs.shop.service.ProductService;
import com.egs.shop.util.MathUtils;
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
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final RateRepository rateRepository;
    private final CommentRepository commentRepository;
    private final ProductMapper productMapper;

    @Override
    public Page<ProductDTO> getEnabledProductsByCategoryId(Long id, Pageable pageable) {
        return productRepository.findByEnabledIsTrueAndCategoryIdAndCategoryEnabledIsTrue(id, pageable)
                .map(product ->
                        productMapper.toDto(
                                product,
                                commentRepository.countByProductId(product.getId()),
                                getProductMeanRate(product.getId())
                        )
                );
    }

    @Override
    public Page<ProductDTO> getAllProductsByCategoryId(Long id, Pageable pageable) {
        return productRepository.findByCategoryId(id, pageable)
                .map(product -> productMapper.toDto(
                        product,
                        commentRepository.countByProductId(product.getId()),
                        getProductMeanRate(product.getId())
                ));
    }

    private float getProductMeanRate(Long id) {
        float avg = (float) rateRepository.findAllByProductId(id).stream()
                .map(Rate::getPoint)
                .mapToInt(Integer::new)
                .average()
                .orElse(0);
        return MathUtils.round(avg,1);
    }
}
