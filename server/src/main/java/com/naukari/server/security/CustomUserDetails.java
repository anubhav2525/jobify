package com.naukari.server.security;

import com.naukari.server.model.entity.user.User;
import com.naukari.server.model.enums.UserRole;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public class CustomUserDetails implements UserDetails {

    @Getter
    private final Long id;

    private final String email;
    private final String password;

    @Getter
    private final UserRole role;

    private final boolean isActive;
    private final boolean isAccountVerified;

    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Long id, String email, String password, UserRole role,
                             boolean isActive, boolean isAccountVerified,
                             Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.isActive = isActive;
        this.isAccountVerified = isAccountVerified;
        this.authorities = authorities;
    }

    public static CustomUserDetails create(User user) {
        Collection<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + user.getRole().name())
        );

        return new CustomUserDetails(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getRole(),
                user.isActive(),
                user.isAccountVerified(),
                authorities
        );
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Can add logic if needed
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Can lock accounts if needed
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Password expiration logic can be added here
    }

    @Override
    public boolean isEnabled() {
        // Log or debug to confirm this is correctly tied to your `isActive` flag
        System.out.println("Checking if user is enabled: " + email + " -> " + isActive);
        return isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomUserDetails that = (CustomUserDetails) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
