package com.taehyeong.chulcheck.config;

import com.taehyeong.backend.authentication.AuthenticationProviderImpl;
import com.taehyeong.backend.authentication.CustomAuthenticationFailureHandler;
import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${frontend.url}")
    private String frontendUrl;
    private final AuthenticationProviderImpl authenticationProvider;
    private final CustomAuthenticationFailureHandler authenticationFailureHandler;

    @Bean
    SecurityFilterChain securityFilterChain(AuthenticationManagerBuilder auths, HttpSecurity http) throws Exception {



        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> {
                    auth
//                            .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                            .requestMatchers("/", "/login", "/member").permitAll()
//                            .requestMatchers("/", "/auth/**", "/js/**", "/css/**", "/img/**", "/fontawesome-free-6.5.1-web/**").permitAll()
                            .anyRequest().authenticated();
                })
                .formLogin(
                        login ->
                                login.defaultSuccessUrl("/")
                                        .failureHandler(authenticationFailureHandler)
                );

                return http.build();
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(frontendUrl));
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", configuration);
        configuration.setExposedHeaders(Arrays.asList("Authorization", "Authorization-Refresh", "Set-Cookie"));

        return urlBasedCorsConfigurationSource;

    }

}
