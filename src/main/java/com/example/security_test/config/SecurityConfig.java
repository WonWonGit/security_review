package com.example.security_test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;



@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig{

    @Bean
    public BCryptPasswordEncoder encodePwd(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.authorizeRequests(authorize -> authorize.antMatchers("/admin/**")
                    .hasRole("ADMIN")
                    .antMatchers("/manager/**")
                    .hasAnyRole("MANAGER", "ADMIN")
                    .antMatchers("/user/**").authenticated()
                    .anyRequest().permitAll()
        );
        httpSecurity.formLogin().loginPage("/loginForm").loginProcessingUrl("/login").defaultSuccessUrl("/");
        httpSecurity.csrf().disable();
        return httpSecurity.build();         
    }
}
