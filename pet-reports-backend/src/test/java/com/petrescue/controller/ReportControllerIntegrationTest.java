package com.petrescue.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petrescue.config.TestConfig;
import com.petrescue.dto.report.CreateReportRequest;
import com.petrescue.model.Report;
import com.petrescue.model.User;
import com.petrescue.repository.ReportRepository;
import com.petrescue.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(TestConfig.class)
public class ReportControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private UserRepository userRepository;

    private User testUser;
    private Report testReport;

    @BeforeEach
    void setUp() {
        reportRepository.deleteAll();
        userRepository.deleteAll();

        testUser = new User();
        testUser.setEmail("test@example.com");
        testUser.setPassword("password");
        testUser.setName("Test User");
        testUser = userRepository.save(testUser);

        testReport = new Report();
        testReport.setUser(testUser);
        testReport.setAnimalType("Dog");
        testReport.setDescription("Found a lost dog");
        testReport.setImageUrl("https://example.com/image.jpg");
        testReport.setLatitude(40.7128);
        testReport.setLongitude(-74.0060);
        testReport.setDateFound(LocalDateTime.now());
        testReport.setIsResolved(false);
        testReport = reportRepository.save(testReport);
    }

    @Test
    @WithMockUser(username = "test@example.com")
    void createReport_Success() throws Exception {
        CreateReportRequest request = new CreateReportRequest();
        request.setAnimalType("Cat");
        request.setDescription("Found a lost cat");
        request.setLatitude(40.7128);
        request.setLongitude(-74.0060);

        MockMultipartFile image = new MockMultipartFile(
            "image",
            "test.jpg",
            MediaType.IMAGE_JPEG_VALUE,
            "test image content".getBytes()
        );

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/reports")
                .file(image)
                .param("animalType", request.getAnimalType())
                .param("description", request.getDescription())
                .param("latitude", String.valueOf(request.getLatitude()))
                .param("longitude", String.valueOf(request.getLongitude()))
                .contentType(MediaType.MULTIPART_FORM_DATA))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.animalType").value(request.getAnimalType()))
            .andExpect(jsonPath("$.description").value(request.getDescription()));
    }

    @Test
    @WithMockUser(username = "test@example.com")
    void getReportById_Success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/reports/{id}", testReport.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(testReport.getId().toString()))
            .andExpect(jsonPath("$.animalType").value(testReport.getAnimalType()))
            .andExpect(jsonPath("$.description").value(testReport.getDescription()));
    }

    @Test
    @WithMockUser(username = "test@example.com")
    void markAsResolved_Success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/reports/{id}/resolve", testReport.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.isResolved").value(true));
    }
} 