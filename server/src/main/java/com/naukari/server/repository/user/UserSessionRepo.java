package com.naukari.server.repository.user;

import com.naukari.server.model.entity.user.User;
import com.naukari.server.model.entity.user.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserSessionRepo extends JpaRepository<UserSession, Long> {

    // Find all sessions for a specific user
    List<UserSession> findByUserId(User user);

    // Find all active sessions for a user
    List<UserSession> findByUserIdAndIsActiveTrue(User user);

    // Find a session by ID and user (secure session ownership check)
    Optional<UserSession> findByIdAndUserId(Long id, User user);

    // Invalidate sessions (e.g., logout all other sessions)
    List<UserSession> findAllByUserIdAndIdNot(User user, Long currentSessionId);

    // Find all expired sessions
    List<UserSession> findAllByExpiresAtBefore(LocalDateTime now);

    // Find session by IP address and device (optional for tracking/logging)
    List<UserSession> findAllByIpAddressAndDeviceInfo(String ipAddress, String deviceInfo);
}