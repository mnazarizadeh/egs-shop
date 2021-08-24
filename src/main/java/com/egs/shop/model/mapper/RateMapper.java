package com.egs.shop.model.mapper;

import com.egs.shop.model.Rate;
import com.egs.shop.model.dto.RateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {UserMapper.class, ProductMapper.class},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RateMapper {

    RateDTO toDto(Rate entity);

    Rate toEntity(RateDTO dto);

}
