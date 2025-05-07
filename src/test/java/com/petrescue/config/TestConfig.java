package com.petrescue.config;

import com.cloudinary.Cloudinary;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.Map;

@TestConfiguration
@ActiveProfiles("test")
public class TestConfig {

    @Bean
    @Primary
    public Cloudinary cloudinary() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "test-cloud");
        config.put("api_key", "test-key");
        config.put("api_secret", "test-secret");
        return new Cloudinary(config);
    }
} 