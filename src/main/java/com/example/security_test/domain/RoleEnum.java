package com.example.security_test.domain;

public enum RoleEnum{
    ADMIN("ROLE_ADMIN"),
    MANAGER("ROLE_MANAGER"),
    USER("ROLE_USER");

    private final String role;

    RoleEnum(String role) {
        this.role = role;
    }

    public String role() {
        return role;
    }

}