package com.hcodes.eventus.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    CustomUserDetailService customUserDetailService;    
    JWTAuthEntryPoint authEntryPoint; 


    public SecurityConfig(CustomUserDetailService customUserDetailService, JWTAuthEntryPoint authEntryPoint) {
        this.customUserDetailService = customUserDetailService;
        this.authEntryPoint = authEntryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {    

        http.csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(requests -> 
                requests
                    .requestMatchers("/api/auth/**").permitAll()
                    .anyRequest()
                    .authenticated()
            )
            .exceptionHandling(exception -> 
                exception.authenticationEntryPoint(authEntryPoint)
            )
            .sessionManagement(session -> 
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // No sessions for REST
            );

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build(); 
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter() {
        return new JWTAuthenticationFilter();
    }
    
}
