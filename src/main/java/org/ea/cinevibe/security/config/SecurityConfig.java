package org.ea.cinevibe.security.config;

import org.ea.cinevibe.security.filter.CustomJwtFilter;
import org.ea.cinevibe.security.service.SecurityUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final SecurityUserDetailsService userDetailsService;

    private final CustomJwtFilter jwtFilter;

    public SecurityConfig(SecurityUserDetailsService userDetailsService, CustomJwtFilter jwtFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        var authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        security.
                csrf(AbstractHttpConfigurer::disable).
                sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).
                addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class).
                authorizeHttpRequests(authorize -> authorize.
                        requestMatchers("/api/v1/auth/register", "/api/v1/auth/login").
                        permitAll().
                        anyRequest().
                        authenticated()).
                authenticationProvider(authenticationProvider());
        return security.build();
    }
}
