package com.egs.shop.model.mapper;

import com.egs.shop.model.Authority;
import com.egs.shop.model.User;
import com.egs.shop.model.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "authorities", source = "authorities", qualifiedByName = "toAuthorityNameList")
    UserDTO toDto(User user);

    @Named("toAuthorityNameList")
    default Set<String> toAuthorityNameList(Set<Authority> authorities) {
        return authorities.stream()
                .map(Authority::getName)
                .collect(Collectors.toSet());
    }
}
