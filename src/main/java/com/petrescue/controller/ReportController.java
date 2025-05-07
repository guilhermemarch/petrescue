package com.petrescue.controller;

import com.petrescue.dto.report.CreateReportRequest;
import com.petrescue.dto.report.ReportResponse;
import com.petrescue.model.User;
import com.petrescue.service.ReportService;
import com.petrescue.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reports")
@Tag(name = "Reports", description = "Pet report management")
public class ReportController {

    private final ReportService reportService;
    private final UserService userService;

    public ReportController(ReportService reportService, UserService userService) {
        this.reportService = reportService;
        this.userService = userService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Create a new pet report")
    public ResponseEntity<ReportResponse> createReport(
            @Valid @ModelAttribute CreateReportRequest request,
            @RequestParam("image") MultipartFile image,
            Authentication authentication) {
        User user = userService.getUserByEmail(authentication.getName());
        return ResponseEntity.ok(reportService.createReport(request, image, user.getId()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a report by ID")
    public ResponseEntity<ReportResponse> getReport(@PathVariable UUID id) {
        return ResponseEntity.ok(reportService.getReportById(id));
    }

    @GetMapping("/user")
    @Operation(summary = "Get all reports by the authenticated user")
    public ResponseEntity<List<ReportResponse>> getUserReports(Authentication authentication) {
        User user = userService.getUserByEmail(authentication.getName());
        return ResponseEntity.ok(reportService.getReportsByUser(user.getId()));
    }

    @GetMapping("/search")
    @Operation(summary = "Search reports with filters")
    public ResponseEntity<List<ReportResponse>> searchReports(
            @RequestParam(required = false) String animalType,
            @RequestParam(required = false) Boolean isResolved,
            @RequestParam(required = false) Double minLat,
            @RequestParam(required = false) Double maxLat,
            @RequestParam(required = false) Double minLng,
            @RequestParam(required = false) Double maxLng) {
        return ResponseEntity.ok(reportService.searchReports(
                animalType, isResolved, minLat, maxLat, minLng, maxLng));
    }

    @PatchMapping("/{id}/resolve")
    @Operation(summary = "Mark a report as resolved")
    public ResponseEntity<ReportResponse> markAsResolved(@PathVariable UUID id) {
        return ResponseEntity.ok(reportService.markAsResolved(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a report")
    public ResponseEntity<Void> deleteReport(@PathVariable UUID id) {
        reportService.deleteReport(id);
        return ResponseEntity.noContent().build();
    }
} 