package com.example.ProjectAPI.repository;


import com.example.ProjectAPI.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    // Tìm kiếm theo username
    boolean existsByUsername(String username);
    // Tìm kiếm theo email
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);

}



