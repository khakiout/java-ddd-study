package com.khakiout.study.ddddemo.app.entity;

import com.khakiout.study.ddddemo.domain.entity.Permission;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * User representation to match with Spring Security needs.
 */
public class ApplicationUser implements UserDetails {

    private final String usename;

    public ApplicationUser(String username) {
        this.usename = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Permission> authorities = new HashSet<>(Arrays.asList(Permission.values()));

        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return usename;
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
