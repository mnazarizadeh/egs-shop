package com.egs.shop.web.rest;

import com.egs.shop.exception.UserNotFoundException;
import com.egs.shop.model.dto.LoginVM;
import com.egs.shop.model.dto.ManagedUserVM;
import com.egs.shop.model.dto.UserDTO;
import com.egs.shop.security.SecurityUtils;
import com.egs.shop.security.jwt.JWTFilter;
import com.egs.shop.security.jwt.JWTToken;
import com.egs.shop.service.UserService;
import com.egs.shop.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class UserResource {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody ManagedUserVM registerUserDTO) throws URISyntaxException {
        log.debug("REST request to create User : {}", registerUserDTO.getUsername());

        UserDTO newUser = userService.registerUser(registerUserDTO);
        return ResponseEntity
                .created(new URI("/api/users/" + newUser.getUsername()))
                .body(newUser);

    }

    @PostMapping("/login")
    public ResponseEntity<JWTToken> loginUser(@Valid @RequestBody LoginVM loginVM) {

        String jwt = userService.loginUser(loginVM);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(new JWTToken(jwt));
    }

    @PutMapping("/admin/users/{username:.+}")
    public ResponseEntity<UserDTO> updateUserInfo(@PathVariable String username, @RequestBody UserDTO user) throws URISyntaxException {
        log.debug("REST request to update User : {}", user);

        user.setUsername(username);
        UserDTO result = userService.updateUser(user);
        return ResponseEntity.ok()
                .body(result);
    }

    @GetMapping("/admin/users/{username:.+}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) throws URISyntaxException {
        log.debug("REST request to get User : {}", username);

        UserDTO user = userService.getUserByUsername(username);
        return ResponseEntity.ok()
                .body(user);
    }

    @GetMapping("/admin/users")
    public ResponseEntity<List<UserDTO>> getAllUsers(Pageable pageable) throws URISyntaxException {
        log.debug("REST request to get all Users");

        Page<UserDTO> page = userService.getAllUsers(pageable);
        return ResponseEntity.ok()
                .headers(PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page))
                .body(page.getContent());
    }

    @GetMapping("/users")
    public ResponseEntity<UserDTO> getMyUser() throws URISyntaxException {
        log.debug("REST request to get User : {}", SecurityUtils.getCurrentUserUsername());

        String username = SecurityUtils.getCurrentUserUsername()
                .orElseThrow(UserNotFoundException::new);
        UserDTO user = userService.getUserByUsername(username);
        return ResponseEntity.ok()
                .body(user);
    }

    @GetMapping("/admin/authorities")
    public ResponseEntity<List<String>> getAuthorities() throws URISyntaxException {
        log.debug("REST request to get all Authorities");

        List<String> authorities = userService.getAuthorities();
        return ResponseEntity.ok()
                .body(authorities);
    }
}
