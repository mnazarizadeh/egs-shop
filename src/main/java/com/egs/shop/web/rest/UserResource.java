package com.egs.shop.web.rest;

import com.egs.shop.model.dto.UserDTO;
import com.egs.shop.service.UserService;
import com.egs.shop.web.rest.vm.ManagedUserVM;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserResource {

    private final UserService userService;

    @PostMapping("/users/register")
    public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody ManagedUserVM registerUserDTO) throws URISyntaxException {
        log.debug("REST request to create User : {}", registerUserDTO.getUsername());

        UserDTO newUser = userService.registerUser(registerUserDTO);
        return ResponseEntity
                .created(new URI("/api/users/" + newUser.getUsername()))
                .body(newUser);

    }

    @GetMapping("/users/{username:.+}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) throws URISyntaxException {
        log.debug("REST request to get User : {}", username);

        UserDTO user = userService.getUserByUsername(username);
        return ResponseEntity.ok()
                .body(user);

    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) throws URISyntaxException {
        log.debug("REST request to get User id : {}", id);

        UserDTO user = userService.getUser(id);
        return ResponseEntity.ok()
                .body(user);

    }
}
