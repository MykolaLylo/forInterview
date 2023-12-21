package com.demo_books_shop.models;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_USER,ROLE_AUTHOR,ROLE_ADMIN;


    @Override
    public String getAuthority() {
        return name();
    }
}
