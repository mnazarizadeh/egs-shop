package com.egs.shop.model.mapper;

import com.egs.shop.model.Product;
import com.egs.shop.model.dto.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    ProductDTO toDto(Product entity);

    Product toEntity(ProductDTO dto);

}
