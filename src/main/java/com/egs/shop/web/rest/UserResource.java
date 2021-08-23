package com.egs.shop.web.rest;

import com.egs.shop.exception.UserNotFoundException;
import com.egs.shop.model.User;
import com.egs.shop.model.dto.UserDTO;
import com.egs.shop.security.AuthoritiesConstants;
import com.egs.shop.security.SecurityUtils;
import com.egs.shop.security.jwt.JWTFilter;
import com.egs.shop.security.jwt.JWTToken;
import com.egs.shop.security.jwt.TokenProvider;
import com.egs.shop.service.UserService;
import com.egs.shop.web.rest.vm.LoginVM;
import com.egs.shop.web.rest.vm.ManagedUserVM;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;


    @PostMapping("/users/register")
    public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody ManagedUserVM registerUserDTO) throws URISyntaxException {
        log.debug("REST request to create User : {}", registerUserDTO.getUsername());

        UserDTO newUser = userService.registerUser(registerUserDTO);
        return ResponseEntity
                .created(new URI("/api/users/" + newUser.getUsername()))
                .body(newUser);

    }

    @PostMapping("/users/login")
    public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginVM loginVM) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication, loginVM.isRememberMe());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(new JWTToken(jwt));
    }

    @GetMapping("/admin/users/{username:.+}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) throws URISyntaxException {
        log.debug("REST request to get User : {}", username);

        UserDTO user = userService.getUserByUsername(username);
        return ResponseEntity.ok()
                .body(user);
    }

    @GetMapping("/admin/users")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<List<UserDTO>> getAllUsers() throws URISyntaxException {
        log.debug("REST request to get all Users");

        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok()
                .body(users);
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

}
