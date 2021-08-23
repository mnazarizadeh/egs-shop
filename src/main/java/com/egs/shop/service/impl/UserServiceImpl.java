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
import com.egs.shop.repository.RoleRepository;
import com.egs.shop.repository.UserRepository;
import com.egs.shop.service.UserService;
import com.egs.shop.web.rest.vm.ManagedUserVM;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

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
        newUser.setEnable(true);
        newUser.setCreateDate(LocalDateTime.now());

        Set<Authority> authorities = new HashSet<>();
        roleRepository.findById(AuthoritiesConstants.USER).ifPresent(authorities::add);
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

    private boolean removeNonActivatedUser(User existingUser) {
        if (existingUser.isActivated()) {
            return false;
        }
        userRepository.delete(existingUser);
        userRepository.flush();
        return true;
    }
}
