package com.example.security_test.domain;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;


public enum OauthAttributes {

    GOOGLE("google", (attributes) -> {
        return User.builder()
                   .providerId((String) attributes.get("sub"))
                   .provider("google")
                   .username("google_"+(String) attributes.get("sub"))
                   .email((String) attributes.get("email"))
                   .role(RoleEnum.USER.role())
                   .build();  
    }),

    NAVER("naver", (attributes) -> {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return User.builder()
                   .providerId((String) response.get("id"))
                   .provider("naver")
                   .username("naver_"+(String) response.get("id"))
                   .email((String) response.get("email"))
                   .role(RoleEnum.USER.role())
                   .build();  
    });

    private final String registrationId;
    private final Function<Map<String, Object>, User> of;

    OauthAttributes(String registrationId, Function<Map<String, Object>, User> of) {
        this.registrationId = registrationId;
        this.of = of;
    }

    public static User extract(String registrationId, Map<String, Object> attributes) {

        User user =  Arrays.stream(values())
                     .filter(provider -> registrationId.equals(provider.registrationId))
                     .findFirst()
                     .orElseThrow(IllegalArgumentException::new)
                     .of.apply(attributes);

        return user;
    }
}
