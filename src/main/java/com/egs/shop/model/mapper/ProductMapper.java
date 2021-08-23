package com.egs.shop.model.mapper;

import com.egs.shop.model.Category;
import com.egs.shop.model.Product;
import com.egs.shop.model.dto.CategoryDTO;
import com.egs.shop.model.dto.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    ProductDTO toDto(Product entity);

    ProductDTO toDto(Product entity, long commentsCount, float meanRate);

    Product toEntity(ProductDTO dto);

}
