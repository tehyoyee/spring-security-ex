package com.security.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.web.SecurityFilterChain;

public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.oauth2Login(Customizer.withDefaults());
//        http.oauth2Login(c -> c.clientRegistrationRepository(clientRepository()));

        http.authorizeHttpRequests(auth -> auth
                .anyRequest()
                .authenticated());

        return http.build();
    }

//    private ClientRegistration clientRegistration() {
//        return CommonOAuth2Provider.GITHUB.getBuilder("github")
//                .clientId("")
//                .clientSecret("")
//                .build();
//    }

    // ClientRegistration을 Repository에 등록.
    // 이 과정은 OAuth2Login(Customizer가 대신 해 줄수 있다)
//    @Bean
//    public ClientRegistrationRepository clientRepository() {
//        var c = clientRegistration();
//        return new InMemoryClientRegistrationRepository(c);
//    }


//    private ClientRegistration clientRegistration() {
//        ClientRegistration cr = ClientRegistration.withRegistrationId("github")
//                .clientId("")
//                .clientSecret("")
//                .scope(new String[]{"read:user"})
//                .authorizationUri("https://github.com/login/oauth/authorize")
//                .tokenUri("https://github.com/login/oauth/access_token")
//                .userInfoUri("https://api.github.com/user")
//                .userNameAttributeName("id")
//                .clientName("GitHub")
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .redirectUri("{baseUrl}/{action}/oauth2/code/{registrationId}")
//                .build();
//        return cr;
//    }
}
