package com.example.security_test.config.auth;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.security_test.auth.PrincipalDetails;
import com.example.security_test.model.User;
import com.example.security_test.repository.UserRepository;
import com.example.security_test.type.RoleEnum;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService{

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getClientId();
        String providerId = oAuth2User.getAttribute("sub");
        String username = provider+"_"+providerId;
        String password = bCryptPasswordEncoder.encode(UUID.randomUUID().toString());
        String email = oAuth2User.getAttribute("email");
        String role = RoleEnum.USER.role();

        User userEntity = userRepository.findByUsername(username);

        if(userEntity == null){
            userEntity = User.builder()
                            .username(username)
                            .email(email)
                            .password(password)
                            .provider(provider)
                            .providerId(providerId)
                            .role(role)
                            .build();
            userRepository.save(userEntity);                
        }


        return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
    }
    
}
