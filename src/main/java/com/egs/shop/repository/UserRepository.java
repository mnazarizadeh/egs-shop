package com.egs.shop.repository;

import com.egs.shop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Set<User> findAllByActivatedIsTrue();

    Optional<User> findByEmailIgnoreCase(String email);
}
