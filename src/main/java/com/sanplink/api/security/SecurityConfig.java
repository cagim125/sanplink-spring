package com.sanplink.api.security;

import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // CSRF 비활성화
        http.csrf((csrf) -> csrf.disable());

        // 세션을 사용하지 않도록 설정 (JWT 기반)
        http.sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        // 로그인 API는 누구나 접근 가능
        http.authorizeHttpRequests((authorize) ->
                authorize.requestMatchers( "/api/auth/**", "/api/presigned-url").permitAll()
                        .anyRequest().authenticated()  // 그 외의 모든 요청은 인증 필요

        );


        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

//        http.formLogin((formLogin) -> formLogin.loginPage("/users/login")
//                .defaultSuccessUrl("http://localhost:3000/")
//                .failureUrl("http://localhost:3000/login")
//
//        );
//        http.logout(logout -> logout
//                .logoutUrl("/api/logout")
//                .logoutSuccessHandler((request, response, authentication) -> {
//                    response.setStatus(HttpServletResponse.SC_OK);
//                })
//        );

        return http.build();
    }
}