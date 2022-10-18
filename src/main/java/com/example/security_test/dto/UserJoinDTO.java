package com.example.security_test.dto;

import com.example.security_test.domain.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserJoinDTO {
    private String username;
    private String password;
    private String email;
    private String role;

    public User toEntity(){
        return User.builder()
                    .email(email)
                    .role(role)
                    .username(username)
                    .password(password)
                    .build();
    }
}
