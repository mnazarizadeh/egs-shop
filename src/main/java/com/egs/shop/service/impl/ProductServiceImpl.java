package com.egs.shop.service.impl;

import com.egs.shop.exception.CategoryNotFoundException;
import com.egs.shop.exception.ProductNotFoundException;
import com.egs.shop.model.Category;
import com.egs.shop.model.Product;
import com.egs.shop.model.Rate;
import com.egs.shop.model.dto.ProductDTO;
import com.egs.shop.model.mapper.ProductMapper;
import com.egs.shop.repository.CategoryRepository;
import com.egs.shop.repository.CommentRepository;
import com.egs.shop.repository.ProductRepository;
import com.egs.shop.repository.RateRepository;
import com.egs.shop.search.ProductSpecificationBuilder;
import com.egs.shop.service.ProductService;
import com.egs.shop.util.MathUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final RateRepository rateRepository;
    private final CommentRepository commentRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(CategoryNotFoundException::new);

        Product newProduct = productMapper.toEntity(productDTO);
        newProduct.setCreateDate(LocalDateTime.now());
        newProduct.setEnabled(true);
        newProduct.setCategory(category);

        return productMapper.toDto(productRepository.save(newProduct));
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(productDTO.getId())
                .orElseThrow(ProductNotFoundException::new);

        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(CategoryNotFoundException::new);

        Product product = productMapper.toEntity(productDTO);
        product.setCreateDate(existingProduct.getCreateDate());
        product.setCategory(category);

        return productMapper.toDto(productRepository.save(product));
    }

    @Override
    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(productMapper::toDto);
    }

    @Override
    public Page<ProductDTO> getEnabledProducts(Pageable pageable) {
        return productRepository.findAllByEnabledIsTrue(pageable)
                .map(productMapper::toDto);
    }

    @Override
    public ProductDTO getProductById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toDto)
                .orElseThrow(ProductNotFoundException::new);
    }

    @Override
    public ProductDTO getEnabledProductById(Long id) {
        return productRepository.findByIdAndEnabledIsTrue(id)
                .map(productMapper::toDto)
                .orElseThrow(ProductNotFoundException::new);
    }

    @Override
    public void deleteById(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException();
        }

        productRepository.deleteById(id);
    }

    @Override
    public Page<ProductDTO> getEnabledProductsByCategoryId(Long id, Pageable pageable) {
        return productRepository.findByEnabledIsTrueAndCategoryIdAndCategoryEnabledIsTrue(id, pageable)
                .map(productMapper::toDto
                );
    }

    @Override
    public Page<ProductDTO> getAllProductsByCategoryId(Long id, Pageable pageable) {
        return productRepository.findByCategoryId(id, pageable)
                .map(productMapper::toDto);
    }

    @Override
    public ProductDTO updateCommentsInfo(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);

        product.setCommentsCount(commentRepository.countByProduct(product));
        return productMapper.toDto(productRepository.save(product));
    }

    @Override
    public ProductDTO updateRatesInfo(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);

        List<Rate> productRates = rateRepository.findAllByProductId(product.getId());

        float avg = (float) productRates
                .stream()
                .map(Rate::getPoint)
                .mapToInt(Integer::new)
                .average()
                .orElse(0);

        product.setRate(MathUtils.round(avg,1));
        product.setRatesCount(productRates.size());

        return productMapper.toDto(productRepository.save(product));
    }

    @Override
    public List<ProductDTO> search(String search) {
        ProductSpecificationBuilder specificationBuilder = new ProductSpecificationBuilder();

        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w.+?),");
        Matcher matcher = pattern.matcher(search + ",");

        specificationBuilder.with("enabled", ":", true);
        while (matcher.find()) {
            specificationBuilder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }

        Specification<Product> spec = specificationBuilder.build();
        return productRepository.findAll(spec).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

}
