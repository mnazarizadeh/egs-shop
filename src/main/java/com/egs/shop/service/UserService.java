package com.egs.shop.service;

import com.egs.shop.model.dto.UserDTO;
import com.egs.shop.web.rest.vm.ManagedUserVM;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserService {

    UserDTO registerUser(ManagedUserVM registerUserDTO);

    UserDTO getUserByUsername(String username);

    UserDTO getUser (Long id);

    List<UserDTO> getAllUsers();
}
