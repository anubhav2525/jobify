package com.naukari.server.repository.user;

import com.naukari.server.model.entity.user.User;
import com.naukari.server.model.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    // Find by email (used in login, registration etc.)
    Optional<User> findByEmail(String email);

    // Check if user with email exists
    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.isActive = true")
    List<User> findAllActiveUsers();

    // Find all active users
    List<User> findAllByIsActiveTrue();

    // Custom: Find all users by role
    List<User> findAllByRole(UserRole role);

    // Custom: Search by name (example for partial match)
    @Query("SELECT u FROM User u WHERE LOWER(u.firstName) LIKE LOWER(CONCAT('%', :name, '%')) " +
            "OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<User> searchByName(String name);
}