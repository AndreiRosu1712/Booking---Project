package com.example.Project_IP.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // permite toate endpoint-urile
                .allowedOrigins("http://localhost:3000") // permite frontend-ul React
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // metode permise
                .allowedHeaders("*") // permite orice header
                .allowCredentials(true); // dacă trimiți cookie-uri/token
    }
}
