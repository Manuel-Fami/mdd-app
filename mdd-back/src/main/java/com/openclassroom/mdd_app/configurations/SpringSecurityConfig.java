package com.openclassroom.mdd_app.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import com.openclassroom.mdd_app.filters.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CorsConfigurationSource corsConfigurationSource;
	 
    public SpringSecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, AuthenticationProvider authenticationProvider, CorsConfigurationSource corsConfigurationSource) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.corsConfigurationSource = corsConfigurationSource;

    }
	 	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {     

		return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/auth/register", "/auth/login", "/swagger-ui/*", "/v3/api-docs", "/v3/api-docs/*").permitAll() 
                    .anyRequest().authenticated()) 
                // Vérification de l'utilisateur
                .authenticationProvider(authenticationProvider)
                // Gestion de l'authentification basé sur l'utilisateur. Le token est validé avant tout autre filtre
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)                
                .build();        
    }
}
