package com.example.config;

import com.example.entity.RegistrationSource;
import com.example.entity.UserEntity;
import com.example.entity.UserRole;
import com.example.entity.dto.NaverUserDto;
import com.example.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UserService userService;

    @Value("${frontend.url}")
    private String frontendUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        System.out.println("============OAuth2LoginSuccessHandler====================");
        System.out.println();
        System.out.println("============Authentication=========");
        System.out.println(authentication);

        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        System.out.println("============OAuth2AuthenticationToken================");
        System.out.println(oAuth2AuthenticationToken);
//        if ("github".equals(oAuth2AuthenticationToken.getAuthorizedClientRegistrationId())) {
//            System.out.println("GithubLogin");
//            DefaultOAuth2User principal = (DefaultOAuth2User) authentication.getPrincipal();
//            Map<String, Object> attributes = principal.getAttributes();
//            String email = attributes.getOrDefault("email", "").toString();
//            String name = attributes.getOrDefault("name", "").toString();
//            System.out.println("email : " + email);
//            userService.findByEmail(email)
//                    .ifPresentOrElse(user -> {
//                        DefaultOAuth2User newUser = new DefaultOAuth2User(List.of(new SimpleGrantedAuthority(user.getRole().name())),
//                                attributes, "id");
//                        Authentication securityAuth = new OAuth2AuthenticationToken(newUser, List.of(new SimpleGrantedAuthority(user.getRole().name())),
//                                oAuth2AuthenticationToken.getAuthorizedClientRegistrationId());
//                        SecurityContextHolder.getContext().setAuthentication(securityAuth);
//                    }, () -> {
//                        UserEntity userEntity = new UserEntity();
//                        userEntity.setRole(UserRole.ROLE_USER);
//                        userEntity.setEmail(email);
//                        userEntity.setName(name);
//                        userEntity.setSource(RegistrationSource.GITHUB);
//                        userService.save(userEntity);
//                        DefaultOAuth2User newUser = new DefaultOAuth2User(List.of(new SimpleGrantedAuthority(userEntity.getRole().name())),
//                                attributes, "id");
//                        Authentication securityAuth = new OAuth2AuthenticationToken(newUser, List.of(new SimpleGrantedAuthority(userEntity.getRole().name())),
//                                oAuth2AuthenticationToken.getAuthorizedClientRegistrationId());
//                        SecurityContextHolder.getContext().setAuthentication(securityAuth);
//                    });
//        }
        if ("google".equals(oAuth2AuthenticationToken.getAuthorizedClientRegistrationId())) {
            System.out.println("============Google Login============");
            DefaultOAuth2User principal = (DefaultOAuth2User) authentication.getPrincipal();
            System.out.println("============Principal==================");
            System.out.println(principal);
            Map<String, Object> attributes = principal.getAttributes();
            System.out.println("=============attributes=============\n" + attributes);
            String email = attributes.getOrDefault("email", "").toString();
            String name = attributes.getOrDefault("name", "").toString();
            System.out.println("email : " + email);
            userService.findByEmail(email)
                    .ifPresentOrElse(user -> {
                        DefaultOAuth2User newUser = new DefaultOAuth2User(List.of(new SimpleGrantedAuthority(user.getRole().name())),
                                attributes, "email");
                        Authentication securityAuth = new OAuth2AuthenticationToken(newUser, List.of(new SimpleGrantedAuthority(user.getRole().name())),
                                oAuth2AuthenticationToken.getAuthorizedClientRegistrationId());
                        SecurityContextHolder.getContext().setAuthentication(securityAuth);
                    }, () -> {
                        UserEntity userEntity = new UserEntity();
                        userEntity.setRole(UserRole.ROLE_USER);
                        userEntity.setEmail(email);
                        userEntity.setName(name);
                        userEntity.setSource(RegistrationSource.GOOGLE);
                        userService.save(userEntity);
                        DefaultOAuth2User newUser = new DefaultOAuth2User(List.of(new SimpleGrantedAuthority(userEntity.getRole().name())),
                                attributes, "email");
                        Authentication securityAuth = new OAuth2AuthenticationToken(newUser, List.of(new SimpleGrantedAuthority(userEntity.getRole().name())),
                                oAuth2AuthenticationToken.getAuthorizedClientRegistrationId());
                        SecurityContextHolder.getContext().setAuthentication(securityAuth);
                    });
        }

        else if ("naver".equals(oAuth2AuthenticationToken.getAuthorizedClientRegistrationId())) {
            System.out.println("==================Naver Login=================");
            DefaultOAuth2User principal = (DefaultOAuth2User) authentication.getPrincipal();
            System.out.println("===========principal============\n" + principal);
            Map<String, Object> attributes = principal.getAttributes();
            System.out.println("===========attribute============\n" + attributes);
            Map<String, Object> responseNaver = (Map<String, Object>)attributes.get("response");
            System.out.println("===========responseNaver==============\n" + responseNaver);
            String email = responseNaver.getOrDefault("id", "").toString();
            String name = responseNaver.getOrDefault("name", "").toString();
            System.out.println("email : " + email);
            userService.findByEmail(email)
                    .ifPresentOrElse(user -> {
                        DefaultOAuth2User newUser = new DefaultOAuth2User(List.of(new SimpleGrantedAuthority(user.getRole().name())),
                                responseNaver, "email");
                        Authentication securityAuth = new OAuth2AuthenticationToken(newUser, List.of(new SimpleGrantedAuthority(user.getRole().name())),
                                oAuth2AuthenticationToken.getAuthorizedClientRegistrationId());
                        SecurityContextHolder.getContext().setAuthentication(securityAuth);
                    }, () -> {
                        UserEntity userEntity = new UserEntity();
                        userEntity.setRole(UserRole.ROLE_USER);
                        userEntity.setEmail(email);
//                        userEntity.setName(name);
                        userEntity.setSource(RegistrationSource.NAVER);
                        userService.save(userEntity);
                        DefaultOAuth2User newUser = new DefaultOAuth2User(List.of(new SimpleGrantedAuthority(userEntity.getRole().name())),
                                responseNaver, "email");
                        Authentication securityAuth = new OAuth2AuthenticationToken(newUser, List.of(new SimpleGrantedAuthority(userEntity.getRole().name())),
                                oAuth2AuthenticationToken.getAuthorizedClientRegistrationId());
                        SecurityContextHolder.getContext().setAuthentication(securityAuth);
                    });
        } else if ("kakao".equals(oAuth2AuthenticationToken.getAuthorizedClientRegistrationId())) {
            System.out.println("===================kakao Login==================");
            DefaultOAuth2User principal = (DefaultOAuth2User) authentication.getPrincipal();
            System.out.println("===================principal==================\n" + principal);
            Map<String, Object> attributes = principal.getAttributes();
            System.out.println("==================attribute================\n" + attributes);
//            Map<String, String> responseKakao = (Map<String, String>)attributes.get("id");
//            System.out.println(attributes);
            String email = attributes.getOrDefault("id", "").toString();
//            String name = responseNaver.getOrDefault("name", "").toString();
//            System.out.println("email : " + email);
            userService.findByEmail(email)
                    .ifPresentOrElse(user -> {
                        DefaultOAuth2User newUser = new DefaultOAuth2User(List.of(new SimpleGrantedAuthority(user.getRole().name())),
                                attributes, "id");
                        Authentication securityAuth = new OAuth2AuthenticationToken(newUser, List.of(new SimpleGrantedAuthority(user.getRole().name())),
                                oAuth2AuthenticationToken.getAuthorizedClientRegistrationId());
                        SecurityContextHolder.getContext().setAuthentication(securityAuth);
                    }, () -> {
                        UserEntity userEntity = new UserEntity();
                        userEntity.setRole(UserRole.ROLE_USER);
                        userEntity.setEmail(email);
                        userEntity.setName("kakaouser");
                        userEntity.setSource(RegistrationSource.KAKAO);
                        userService.save(userEntity);
                        DefaultOAuth2User newUser = new DefaultOAuth2User(List.of(new SimpleGrantedAuthority(userEntity.getRole().name())),
                                attributes, "id");
                        Authentication securityAuth = new OAuth2AuthenticationToken(newUser, List.of(new SimpleGrantedAuthority(userEntity.getRole().name())),
                                oAuth2AuthenticationToken.getAuthorizedClientRegistrationId());
                        SecurityContextHolder.getContext().setAuthentication(securityAuth);
                    });
        }



        this.setAlwaysUseDefaultTargetUrl(true);
        this.setDefaultTargetUrl(frontendUrl);
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
