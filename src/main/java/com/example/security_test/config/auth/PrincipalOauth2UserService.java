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
import com.example.security_test.domain.User;
import com.example.security_test.domain.OauthAttributes;
import com.example.security_test.domain.RoleEnum;
import com.example.security_test.repository.UserRepository;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService{

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId();

        User userEntity = OauthAttributes.extract(provider, oAuth2User.getAttributes());
        
        User findUser = userRepository.findByUsername(userEntity.getUsername());

        if(findUser == null){

            userEntity.setPassword(bCryptPasswordEncoder.encode(UUID.randomUUID().toString()));
            
            userRepository.save(userEntity);                
        }


        return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
    }
    
}
