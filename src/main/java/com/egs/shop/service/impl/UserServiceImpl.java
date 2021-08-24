package com.egs.shop.service.impl;

import com.egs.shop.exception.EmailAlreadyUsedException;
import com.egs.shop.exception.PasswordMismatchedException;
import com.egs.shop.exception.UsernameAlreadyUsedException;
import com.egs.shop.exception.UserNotFoundException;
import com.egs.shop.model.Authority;
import com.egs.shop.model.User;
import com.egs.shop.security.AuthoritiesConstants;
import com.egs.shop.model.dto.UserDTO;
import com.egs.shop.model.mapper.UserMapper;
import com.egs.shop.repository.AuthorityRepository;
import com.egs.shop.repository.UserRepository;
import com.egs.shop.security.jwt.TokenProvider;
import com.egs.shop.service.UserService;
import com.egs.shop.model.dto.LoginVM;
import com.egs.shop.model.dto.ManagedUserVM;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;

    @Override
    public UserDTO registerUser(ManagedUserVM userVM) {
        userRepository.findByUsername(userVM.getUsername().toLowerCase())
                .ifPresent(
                        existingUser -> {
                            boolean removed = removeNonActivatedUser(existingUser);
                            if (!removed) {
                                throw new UsernameAlreadyUsedException();
                            }
                        }
                );
        userRepository.findByEmailIgnoreCase(userVM.getEmail())
                .ifPresent(
                        existingUser -> {
                            boolean removed = removeNonActivatedUser(existingUser);
                            if (!removed) {
                                throw new EmailAlreadyUsedException();
                            }
                        }
                );
        if (!userVM.getPassword().equals(userVM.getConfirmPassword())) {
            throw new PasswordMismatchedException();
        }

        User newUser = new User();
        newUser.setUsername(userVM.getUsername().toLowerCase());
        newUser.setPassword(passwordEncoder.encode(userVM.getPassword()));
        newUser.setEmail(userVM.getEmail().toLowerCase());
        // new user is pre active
        newUser.setActivated(true);
        // new user is unblock
        newUser.setBlocked(false);
        newUser.setCreateDate(LocalDateTime.now());

        Set<Authority> authorities = new HashSet<>();
        authorityRepository.findById(AuthoritiesConstants.USER).ifPresent(authorities::add);
        newUser.setAuthorities(authorities);

        userRepository.save(newUser);
        log.debug("Created Information for User: {}", newUser);
        return userMapper.toDto(newUser);
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
        return userMapper.toDto(user);
    }

    @Override
    public UserDTO getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        return userMapper.toDto(user);
    }

    @Override
    public Page<UserDTO> getAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);

        return users.map(userMapper::toDto);
    }

    @Override
    public String loginUser(LoginVM loginVM) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return tokenProvider.createToken(authentication, loginVM.isRememberMe());
    }

    /**
     * Gets a list of all the authorities.
     * @return a list of all the authorities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<String> getAuthorities() {
        return authorityRepository.findAll().stream()
                .map(Authority::getName)
                .collect(Collectors.toList());
    }

    /**
     * Update all information for a specific user, and return the modified user.
     *
     * @param userDTO user to update.
     * @return updated user.
     */
    @Override
    public UserDTO updateUser(UserDTO userDTO) {
        Optional<User> existingUser = userRepository.findByEmailIgnoreCase(userDTO.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getUsername().equals(userDTO.getUsername()))) {
            throw new EmailAlreadyUsedException();
        }

        return userRepository.findByUsername(userDTO.getUsername().toLowerCase())
                .map(
                        user -> {
                            user.setUsername(userDTO.getUsername().toLowerCase());
                            if (userDTO.getEmail() != null) {
                                user.setEmail(userDTO.getEmail().toLowerCase());
                            }
                            user.setBlocked(userDTO.isBlocked());
                            Set<Authority> managedAuthorities = user.getAuthorities();
                            managedAuthorities.clear();
                            userDTO
                                    .getAuthorities()
                                    .stream()
                                    .map(authorityRepository::findById)
                                    .filter(Optional::isPresent)
                                    .map(Optional::get)
                                    .forEach(managedAuthorities::add);
                            log.debug("Changed Information for User: {}", user);
                            return user;
                        }
                )
                .map(userMapper::toDto)
                .orElseThrow(UserNotFoundException::new);
    }

    private boolean removeNonActivatedUser(User existingUser) {
        if (existingUser.isActivated()) {
            return false;
        }
        userRepository.delete(existingUser);
        userRepository.flush();
        return true;
    }
}
