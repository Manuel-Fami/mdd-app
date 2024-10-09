package com.openclassroom.mdd_app.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
public class WebConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true); // Si vous avez besoin d'envoyer des cookies
        configuration.addAllowedOrigin("http://localhost:4200"); // L'URL de votre frontend
        configuration.addAllowedMethod("*"); // Autoriser toutes les méthodes (GET, POST, etc.)
        configuration.addAllowedHeader("*"); // Autoriser tous les en-têtes
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
