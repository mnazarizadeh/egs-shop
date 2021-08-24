package com.egs.shop.service.impl;

import com.egs.shop.exception.CategoryAlreadyExistsException;
import com.egs.shop.exception.CategoryNotFoundException;
import com.egs.shop.model.Category;
import com.egs.shop.model.dto.CategoryDTO;
import com.egs.shop.model.mapper.CategoryMapper;
import com.egs.shop.repository.CategoryRepository;
import com.egs.shop.repository.ProductRepository;
import com.egs.shop.service.CategoryService;
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
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        checkDuplicateCategory(categoryDTO);

        Category newCategory = categoryMapper.toEntity(categoryDTO);
        newCategory.setCreateDate(LocalDateTime.now());
        newCategory.setEnabled(true);

        return categoryMapper.toDto(categoryRepository.save(newCategory));
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO) {
        Category existingCategory = categoryRepository.findById(categoryDTO.getId())
                .orElseThrow(CategoryNotFoundException::new);

        checkDuplicateCategory(categoryDTO);

        Category category = categoryMapper.toEntity(categoryDTO);
        category.setCreateDate(existingCategory.getCreateDate());

        return categoryMapper.toDto(
                categoryRepository.save(category),
                productRepository.countByCategory(category));
    }

    @Override
    public Page<CategoryDTO> getAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .map(category -> categoryMapper.toDto(category, productRepository.countByCategory(category)));
    }

    @Override
    public Page<CategoryDTO> getEnabledCategories(Pageable pageable) {
        return categoryRepository.findAllByEnabledIsTrue(pageable)
                .map(category -> categoryMapper.toDto(category, productRepository.countByCategory(category)));
    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(category -> categoryMapper.toDto(category, productRepository.countByCategory(category)))
                .orElseThrow(CategoryNotFoundException::new);
    }

    @Override
    public CategoryDTO getEnabledCategoryById(Long id) {
        return categoryRepository.findByIdAndEnabledIsTrue(id)
                .map(category -> categoryMapper.toDto(category, productRepository.countByCategory(category)))
                .orElseThrow(CategoryNotFoundException::new);
    }

    @Override
    public void deleteById(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new CategoryNotFoundException();
        }

        categoryRepository.deleteById(id);
    }

    private void checkDuplicateCategory(CategoryDTO categoryDTO) {
        categoryRepository.findByTitle(categoryDTO.getTitle())
                .ifPresent( existingCategory -> {
                    if (!existingCategory.getId().equals(categoryDTO.getId())) {
                        throw new CategoryAlreadyExistsException();
                    }
                });
    }
}
