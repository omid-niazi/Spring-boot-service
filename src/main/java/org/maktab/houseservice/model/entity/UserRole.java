package org.maktab.houseservice.model.entity;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {

    ADMIN, CLIENT, EXPERT;

    @Override
    public String getAuthority() {
        return name();
    }
}
