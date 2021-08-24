package com.egs.shop.model.mapper;

import com.egs.shop.model.Comment;
import com.egs.shop.model.dto.CommentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {UserMapper.class, ProductMapper.class},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {

    CommentDTO toDto(Comment entity);

    Comment toEntity(CommentDTO dto);

}
