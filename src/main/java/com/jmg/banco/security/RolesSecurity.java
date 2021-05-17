package com.jmg.banco.security;

import org.springframework.security.core.GrantedAuthority;

public enum RolesSecurity implements GrantedAuthority {
    ROLE_ADMIN, ROLE_CLIENT;

    public String getAuthority() {
        return name();
    }

}
