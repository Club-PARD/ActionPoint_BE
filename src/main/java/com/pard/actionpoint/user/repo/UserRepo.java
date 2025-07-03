package com.pard.actionpoint.user.repo;

import com.pard.actionpoint.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByUserEmail(String userEmail);
}
