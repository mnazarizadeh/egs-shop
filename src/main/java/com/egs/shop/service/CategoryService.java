package com.egs.shop.service;

import com.egs.shop.model.dto.CategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO updateCategory(CategoryDTO categoryDTO);

    Page<CategoryDTO> getAllCategories(Pageable pageable);

    Page<CategoryDTO> getEnabledCategories(Pageable pageable);

    CategoryDTO getCategoryById(Long id);

    CategoryDTO getEnabledCategoryById(Long id);

    void deleteById(Long id);
}
