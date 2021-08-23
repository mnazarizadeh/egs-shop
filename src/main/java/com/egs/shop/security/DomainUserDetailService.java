package com.egs.shop.security;

import com.egs.shop.exception.UserBlockedException;
import com.egs.shop.exception.UserNotActivatedException;
import com.egs.shop.model.User;
import com.egs.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Component("userDetailsService")
@RequiredArgsConstructor
@Slf4j
public class DomainUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Authenticating {}", username);

        String lowercaseUsername = username.toLowerCase(Locale.ENGLISH);

        User user = userRepository.findByUsername(lowercaseUsername)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with username: " + username));

        if (!user.isActivated()) {
            throw new UserNotActivatedException("User " + lowercaseUsername + " was not activated");
        }

        if (user.isBlocked()) {
            throw new UserBlockedException("User " + lowercaseUsername + " has been blocked.");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), getAuthorities(user));
    }

    private static Set<GrantedAuthority> getAuthorities (User user) {
        return user.getAuthorities()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());
    }
}
