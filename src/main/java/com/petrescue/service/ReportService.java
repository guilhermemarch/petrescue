package com.petrescue.service;

import com.petrescue.dto.report.CreateReportRequest;
import com.petrescue.dto.report.ReportResponse;
import com.petrescue.model.Report;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface ReportService {
    ReportResponse createReport(CreateReportRequest request, MultipartFile image, UUID userId);
    ReportResponse getReportById(UUID id);
    List<ReportResponse> getReportsByUser(UUID userId);
    List<ReportResponse> searchReports(String animalType, Boolean isResolved, 
                                     Double minLat, Double maxLat, 
                                     Double minLng, Double maxLng);
    ReportResponse markAsResolved(UUID id);
    void deleteReport(UUID id);
} 