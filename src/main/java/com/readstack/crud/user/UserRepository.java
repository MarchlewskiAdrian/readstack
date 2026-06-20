package com.readstack.crud.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findFirstByEmail(String email);
    Optional<User> findFirstByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
