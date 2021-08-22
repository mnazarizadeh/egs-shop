package com.egs.shop.model.mapper;

import com.egs.shop.model.Role;
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

    @Mapping(target = "roles", source = "roles", qualifiedByName = "toRoleNameList")
    UserDTO toDto(User user);

    @Named("toRoleNameList")
    default Set<String> toRoleNameList(Set<Role> roles) {
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }
}
