package com.novi.security;

import com.novi.entities.User;
import com.novi.entities.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ApiUserDetails implements UserDetails {
    private final User user;

    public ApiUserDetails(User user) {
        this.user = user;
    }


@Override
public Collection<? extends GrantedAuthority> getAuthorities() {
    List<GrantedAuthority> authorities = new ArrayList<>();
    for (Role role: user.getRoles()) {
        if(role.isActive()) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
    }
    return authorities;
}

@Override
public String getPassword() {
    return user.getPassword();
}

@Override
public String getUsername() {
    return user.getEmail();
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
}}

