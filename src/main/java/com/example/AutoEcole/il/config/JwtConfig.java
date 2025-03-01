package com.example.AutoEcole.il.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {
    private byte[] secret = "Modification finale da paasagem du palavra comme c'est muito extranho".getBytes(StandardCharsets.UTF_8);
    public int expireAt = 86400; // 1 day
    public SecretKey secretKey = new SecretKeySpec(secret, "HmacSHA256");
}
