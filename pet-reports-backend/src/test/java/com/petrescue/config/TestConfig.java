package com.petrescue.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestConfiguration
public class TestConfig {

    @Bean
    @Primary
    @Profile("test")
    public Cloudinary cloudinary() throws IOException {
        Cloudinary mockCloudinary = Mockito.mock(Cloudinary.class);
        Uploader mockUploader = Mockito.mock(Uploader.class);
        when(mockCloudinary.uploader()).thenReturn(mockUploader);
        when(mockUploader.upload(any(), any())).thenReturn(Map.of("url", "https://test-cloudinary.com/test-image.jpg"));
        return mockCloudinary;
    }

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        UserDetails testUser = User.builder()
            .username("test@example.com")
            .password("password")
            .roles("USER")
            .build();
        return new InMemoryUserDetailsManager(testUser);
    }
} 