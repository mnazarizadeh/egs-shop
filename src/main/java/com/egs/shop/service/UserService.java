package com.egs.shop.service;

import com.egs.shop.model.dto.UserDTO;
import com.egs.shop.web.rest.vm.ManagedUserVM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public interface UserService {

    UserDTO registerUser(ManagedUserVM registerUserDTO);

    UserDTO getUserByUsername(String username);

    UserDTO getUser (Long id);

    Page<UserDTO> getAllUsers(Pageable pageable);

}
