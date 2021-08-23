package com.egs.shop.model.mapper;

import com.egs.shop.model.Authority;
import com.egs.shop.model.Category;
import com.egs.shop.model.User;
import com.egs.shop.model.dto.CategoryDTO;
import com.egs.shop.model.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    CategoryDTO toDto(Category entity);

    Category toEntity(CategoryDTO dto);

    CategoryDTO toDto(Category entity, long productsCount);
}
