package com.example.security_test.type;

public enum RoleEnum{
    ADMIN("ROLE_ADMIN"),
    MANAGER("ROLE_MANAGER");

    private final String role;

    RoleEnum(String role) {
        this.role = role;
    }

    public String role() {
        return role;
    }

}