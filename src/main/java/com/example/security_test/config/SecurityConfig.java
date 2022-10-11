package com.example.security_test.config;

import java.lang.reflect.Method;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;



@EnableWebSecurity
public class SecurityConfig{

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.authorizeRequests(authorize -> authorize.antMatchers(HttpMethod.GET, "/admin/**")
                    .hasRole("ADMIN")
                    .antMatchers(HttpMethod.GET,"/manager/**")
                    .hasAnyRole("MANAGER", "ADMIN")
                    .anyRequest().permitAll()
        );
        httpSecurity.csrf().disable();
        return httpSecurity.build();         
    }
}
