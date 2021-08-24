package com.egs.shop.model.mapper;

import com.egs.shop.model.Category;
import com.egs.shop.model.dto.CategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    CategoryDTO toDto(Category entity);

    Category toEntity(CategoryDTO dto);

    CategoryDTO toDto(Category entity, long productsCount);

    default Category fromId(Long id) {
        if (id == null) {
            return null;
        }
        Category obj = new Category();
        obj.setId(id);
        return obj;
    }
}
