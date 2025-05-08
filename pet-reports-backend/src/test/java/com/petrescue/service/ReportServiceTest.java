package com.petrescue.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.petrescue.dto.report.CreateReportRequest;
import com.petrescue.dto.report.ReportResponse;
import com.petrescue.exception.FileUploadException;
import com.petrescue.exception.ResourceNotFoundException;
import com.petrescue.model.Report;
import com.petrescue.model.User;
import com.petrescue.repository.ReportRepository;
import com.petrescue.service.impl.ReportServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    @Mock
    private ReportRepository reportRepository;

    @Mock
    private UserService userService;

    @Mock
    private Cloudinary cloudinary;

    @Mock
    private Uploader uploader;

    @InjectMocks
    private ReportServiceImpl reportService;

    private User testUser;
    private CreateReportRequest testRequest;
    private MultipartFile testImage;
    private Report testReport;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(UUID.randomUUID());
        testUser.setName("Test User");
        testUser.setEmail("test@example.com");

        testRequest = new CreateReportRequest();
        testRequest.setAnimalType("Dog");
        testRequest.setDescription("Found a lost dog");
        testRequest.setLatitude(40.7128);
        testRequest.setLongitude(-74.0060);

        testImage = new MockMultipartFile(
            "image",
            "test.jpg",
            MediaType.IMAGE_JPEG_VALUE,
            "test image content".getBytes()
        );

        testReport = new Report();
        testReport.setId(UUID.randomUUID());
        testReport.setUser(testUser);
        testReport.setAnimalType(testRequest.getAnimalType());
        testReport.setDescription(testRequest.getDescription());
        testReport.setImageUrl("https://example.com/image.jpg");
        testReport.setLatitude(testRequest.getLatitude());
        testReport.setLongitude(testRequest.getLongitude());
        testReport.setDateFound(LocalDateTime.now());
        testReport.setIsResolved(false);
    }

    @Test
    void createReport_Success() throws IOException {
        when(cloudinary.uploader()).thenReturn(uploader);
        when(userService.getUserById(any())).thenReturn(testUser);
        when(uploader.upload(any(), any())).thenReturn(Map.of("url", "https://example.com/image.jpg"));
        when(reportRepository.save(any())).thenReturn(testReport);

        ReportResponse response = reportService.createReport(testRequest, testImage, testUser.getId());

        assertNotNull(response);
        assertEquals(testReport.getAnimalType(), response.getAnimalType());
        assertEquals(testReport.getDescription(), response.getDescription());
        assertEquals(testReport.getImageUrl(), response.getImageUrl());
        verify(reportRepository).save(any());
    }

    @Test
    void createReport_FileUploadFailure() throws IOException {
        when(cloudinary.uploader()).thenReturn(uploader);
        when(userService.getUserById(any())).thenReturn(testUser);
        when(uploader.upload(any(), any())).thenThrow(new IOException("Upload failed"));

        assertThrows(FileUploadException.class, () -> 
            reportService.createReport(testRequest, testImage, testUser.getId())
        );
    }

    @Test
    void getReportById_Success() {
        when(reportRepository.findById(any())).thenReturn(Optional.of(testReport));

        ReportResponse response = reportService.getReportById(testReport.getId());

        assertNotNull(response);
        assertEquals(testReport.getId(), response.getId());
        assertEquals(testReport.getAnimalType(), response.getAnimalType());
    }

    @Test
    void getReportById_NotFound() {
        when(reportRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> 
            reportService.getReportById(UUID.randomUUID())
        );
    }

    @Test
    void markAsResolved_Success() {
        when(reportRepository.findById(any())).thenReturn(Optional.of(testReport));
        when(reportRepository.save(any())).thenReturn(testReport);

        ReportResponse response = reportService.markAsResolved(testReport.getId());

        assertNotNull(response);
        assertTrue(response.getIsResolved());
        verify(reportRepository).save(any());
    }
} 