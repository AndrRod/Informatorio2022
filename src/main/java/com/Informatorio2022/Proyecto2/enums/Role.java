package com.Informatorio2022.Proyecto2.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    COLLABORATOR(Code.COLLABORATOR),
    USER(Code.USER),
    OWNER(Code.OWNER);

    private final String authority;

    Role(String authority) {
        this.authority = authority;
    }
    @Override
    public String getAuthority() {
        return authority;
    }

    public class Code {
        public static final String COLLABORATOR = "ROLE_COLLABORATOR";
        public static final String USER = "ROLE_USER";
        public static final String OWNER = "ROLE_OWNER";
    }
}