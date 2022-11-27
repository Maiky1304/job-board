package com.github.maiky1304.jobboard.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum UserRole {

    USER, ADMIN;

    public GrantedAuthority toGrantedAuthority() {
        return new SimpleGrantedAuthority(name());
    }

}
