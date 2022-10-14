package com.example.security_test.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.example.security_test.model.User;

import lombok.Data;

@Data
public class PrincipalDetails implements UserDetails, OAuth2User{

    private User user;
    private Map<String, Object> attribute;

    public PrincipalDetails(User user){
        this.user = user;
    }

    public PrincipalDetails(User user, Map<String, Object> attribute){
        this.user = user;
        this.attribute = attribute;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        
        collect.add(new GrantedAuthority() {
          @Override
          public String getAuthority() {
              
                return user.getRole();
          }  
        });

        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attribute;
    }

    @Override
    public String getName() {
        return null;
    }
    
}
