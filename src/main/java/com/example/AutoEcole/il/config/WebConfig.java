package com.example.AutoEcole.il.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Mapping du chemin pour accéder aux fichiers via /api/V1/inscriptions/file/
        registry.addResourceHandler("/api/V1/inscriptions/file/**") // Chemin d'URL
                .addResourceLocations("file:uploads/"); // Correspondance avec le répertoire des fichiers
    }
}
