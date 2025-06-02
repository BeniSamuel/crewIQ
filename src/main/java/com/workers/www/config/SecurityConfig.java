package com.workers.www.config;

import com.workers.www.security.JwtAuthFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
        return http
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/workers/auth/signup").permitAll()
                        .requestMatchers("/api/workers/auth/login").permitAll()
                        .requestMatchers("/api/workers/user/update/").hasRole("MANAGER")
                        .requestMatchers("/api/workers/user/update/").hasRole("ADMIN")
                        .requestMatchers("/api/workers/assignment/delete/").hasRole("MANAGER")
                        .requestMatchers("/api/workers/assignment/delete/").hasRole("ADMIN")
                        .requestMatchers("/api/workers/assignment/update/").hasRole("MANAGER")
                        .requestMatchers("/api/workers/assignment/update/").hasRole("ADMIN")
                        .requestMatchers("/api/workers/assignment/create").hasRole("MANAGER")
                        .requestMatchers("/api/workers/assignment/create").hasRole("ADMIN")
                        .requestMatchers("/api/workers/salary/create").hasRole("ADMIN")
                        .requestMatchers("/api/workers/salary/create").hasRole("MANAGER")
                        .requestMatchers("/api/workers/salary/update/").hasRole("MANAGER")
                        .requestMatchers("/api/workers/salary/update/").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin(AbstractHttpConfigurer::disable)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}


