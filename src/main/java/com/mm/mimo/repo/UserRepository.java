package com.mm.mimo.repo;

import com.mm.mimo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByPhoneNumber(String phoneNumber);
    Optional<User> findByUsernameOrEmailOrPhoneNumber(String username, String email, String phoneNumber);
    boolean existsByEmailOrUsernameOrPhoneNumber(String email, String username, String phoneNumber);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
}
