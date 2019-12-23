package com.pkrawczynski.jwt.domain;

import org.springframework.security.core.GrantedAuthority;


public enum Role implements GrantedAuthority {

    ADMIN("ADMIN"), USER("USER");
    private final String description;

    Role(String role) {
        this.description = role;
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public String getAuthority() {
        return description;
    }
}