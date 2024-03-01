package com.example.demo;

import java.nio.charset.StandardCharsets;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/api/faq/**", "/api/quizzes/**", "/api/quiz-responses/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/boards/**").permitAll() // POST 요청 허용
                .requestMatchers(HttpMethod.GET, "/boards/**").permitAll() // GET 요청도 허용
                .requestMatchers(HttpMethod.PUT, "/boards/**").permitAll() // PUT 요청도 허용
                .requestMatchers(HttpMethod.DELETE, "/boards/**").permitAll() // DELETE 요청도 허용
                .anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.decoder(jwtDecoder())));
        return http.build();
    }

    @Bean
    public NimbusJwtDecoder jwtDecoder() {
        byte[] secretKeyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec secretKey = new SecretKeySpec(secretKeyBytes, "HMACSHA256");

        return NimbusJwtDecoder.withSecretKey(secretKey).build();
    }
}