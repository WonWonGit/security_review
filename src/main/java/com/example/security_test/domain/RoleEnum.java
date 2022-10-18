package com.example.security_test.domain;

public enum RoleEnum{
    ADMIN("ADMIN"),
    MANAGER("MANAGER"),
    USER("USER");

    private final String role;

    RoleEnum(String role) {
        this.role = role;
    }

    public String role() {
        return role;
    }

}