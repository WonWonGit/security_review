package com.example.security_test.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.security_test.config.auth.PrincipalOauth2UserService;
import com.example.security_test.domain.RoleEnum;



@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig{

    @Autowired
    private PrincipalOauth2UserService principalPauth2UserService;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.authorizeRequests(authorize -> authorize.antMatchers("/admin/**")
                    .hasRole(RoleEnum.ADMIN.name())
                    .antMatchers("/manager/**")
                    .hasAnyRole(RoleEnum.ADMIN.name(),RoleEnum.MANAGER.name())
                    .antMatchers("/user/**").authenticated()
                    .anyRequest().permitAll()
        );
        httpSecurity.formLogin().loginPage("/loginForm")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/");
        httpSecurity.oauth2Login()
                                .loginPage("/loginForm")
                                .userInfoEndpoint()
                                .userService(principalPauth2UserService);
        httpSecurity.csrf().disable();
        return httpSecurity.build();         
    }



}
