package com.egs.shop.model.mapper;

import com.egs.shop.model.Product;
import com.egs.shop.model.dto.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.title", target = "categoryName")
    ProductDTO toDto(Product entity);

    @Mapping(source = "entity.category.id", target = "categoryId")
    @Mapping(source = "entity.category.title", target = "categoryName")
    ProductDTO toDto(Product entity, long commentsCount, float meanRate);

    @Mapping(source = "categoryId", target = "category.id")
    Product toEntity(ProductDTO dto);

}
