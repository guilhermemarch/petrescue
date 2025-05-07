package com.petrescue.dto.report;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ReportResponse {
    private UUID id;
    private String animalType;
    private String description;
    private String imageUrl;
    private Double latitude;
    private Double longitude;
    private LocalDateTime dateFound;
    private Boolean isResolved;
    private String reporterName;
    private String reporterEmail;

}
