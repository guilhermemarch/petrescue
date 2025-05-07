package com.petrescue.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.petrescue.dto.report.CreateReportRequest;
import com.petrescue.dto.report.ReportResponse;
import com.petrescue.exception.FileUploadException;
import com.petrescue.exception.ResourceNotFoundException;
import com.petrescue.model.Report;
import com.petrescue.model.User;
import com.petrescue.repository.ReportRepository;
import com.petrescue.service.ReportService;
import com.petrescue.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final UserService userService;
    private final Cloudinary cloudinary;

    public ReportServiceImpl(ReportRepository reportRepository, 
                           UserService userService,
                           Cloudinary cloudinary) {
        this.reportRepository = reportRepository;
        this.userService = userService;
        this.cloudinary = cloudinary;
    }

    @Override
    public ReportResponse createReport(CreateReportRequest request, MultipartFile image, UUID userId) {
        User user = userService.getUserById(userId);
        String imageUrl = uploadImage(image);

        Report report = new Report();
        report.setUser(user);
        report.setAnimalType(request.getAnimalType());
        report.setDescription(request.getDescription());
        report.setImageUrl(imageUrl);
        report.setLatitude(request.getLatitude());
        report.setLongitude(request.getLongitude());
        report.setDateFound(LocalDateTime.now());
        report.setIsResolved(false);

        report = reportRepository.save(report);
        return mapToResponse(report);
    }

    @Override
    @Transactional(readOnly = true)
    public ReportResponse getReportById(UUID id) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Report", "id", id));
        return mapToResponse(report);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReportResponse> getReportsByUser(UUID userId) {
        return reportRepository.findByUserId(userId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReportResponse> searchReports(String animalType, Boolean isResolved,
                                            Double minLat, Double maxLat,
                                            Double minLng, Double maxLng) {
        return reportRepository.searchReports(animalType, isResolved, minLat, maxLat, minLng, maxLng)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ReportResponse markAsResolved(UUID id) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Report", "id", id));
        report.setIsResolved(true);
        report = reportRepository.save(report);
        return mapToResponse(report);
    }

    @Override
    public void deleteReport(UUID id) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Report", "id", id));
        reportRepository.delete(report);
    }

    private String uploadImage(MultipartFile file) {
        try {
            Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            return (String) uploadResult.get("url");
        } catch (IOException e) {
            throw new FileUploadException("Failed to upload image", e);
        }
    }

    private ReportResponse mapToResponse(Report report) {
        ReportResponse response = new ReportResponse();
        response.setId(report.getId());
        response.setAnimalType(report.getAnimalType());
        response.setDescription(report.getDescription());
        response.setImageUrl(report.getImageUrl());
        response.setLatitude(report.getLatitude());
        response.setLongitude(report.getLongitude());
        response.setDateFound(report.getDateFound());
        response.setIsResolved(report.getIsResolved());
        response.setReporterName(report.getUser().getName());
        response.setReporterEmail(report.getUser().getEmail());
        return response;
    }
} 