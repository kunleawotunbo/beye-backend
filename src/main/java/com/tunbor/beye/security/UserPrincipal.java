package com.tunbor.beye.security;

import com.tunbor.beye.entity.User;
import com.tunbor.beye.entity.UserRole;
import com.tunbor.beye.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Olakunle Awotunbo
 */
@Builder
@AllArgsConstructor
@Getter
public class UserPrincipal implements UserDetails {

    private final Long userId;

    private final String firstName;

    private final String lastName;

    private final String username;

    private final String email;

    private final String password;

    private final boolean enabled;

    private final Collection<? extends GrantedAuthority> authorities;

    private final Collection<Role> roles;

    public static UserPrincipal create(User user) {
        Set<Role> roles = user.getUserRoles().stream()
                .map(UserRole::getRole)
                .collect(Collectors.toSet());

        List<GrantedAuthority> authorities = user.getUserRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getRole().name())
        ).collect(Collectors.toList());

        return UserPrincipal.builder()
                .userId(user.getId())
                .username(user.getEmail())
                .password(user.getPassword())
                .enabled(user.isEnabled())
                .roles(roles)
                .authorities(authorities)
                .build();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}