package com.example.AutoEcole.il.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Configuration
public class CloudinaryConfig {

    // Charger les variables depuis le fichier .env
    private static final Dotenv dotenv = Dotenv.load();

    @Bean
    public Cloudinary cloudinary() {
        String cloudName = dotenv.get("CLOUDINARY_CLOUD_NAME");
        String apiKey = dotenv.get("CLOUDINARY_API_KEY");
        String apiSecret = dotenv.get("CLOUDINARY_API_SECRET");

        // Vérifie si les variables sont chargées
        if (cloudName == null || apiKey == null || apiSecret == null) {
            throw new IllegalStateException("Cloudinary credentials are not set in .env");
        }
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", dotenv.get("CLOUDINARY_CLOUD_NAME"), // Récupère la valeur de CLOUDINARY_CLOUD_NAME
                "api_key", dotenv.get("CLOUDINARY_API_KEY"),       // Récupère la valeur de CLOUDINARY_API_KEY
                "api_secret", dotenv.get("CLOUDINARY_API_SECRET"), // Récupère la valeur de CLOUDINARY_API_SECRET
                "secure", true
        ));
    }
}
