package com.petrescue.repository;

import com.petrescue.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.UUID;

public interface ReportRepository extends JpaRepository<Report, UUID> {
    List<Report> findByUserId(UUID userId);
    
    @Query("SELECT r FROM Report r WHERE " +
           "(:animalType IS NULL OR r.animalType = :animalType) AND " +
           "(:isResolved IS NULL OR r.isResolved = :isResolved) AND " +
           "(:minLat IS NULL OR r.latitude >= :minLat) AND " +
           "(:maxLat IS NULL OR r.latitude <= :maxLat) AND " +
           "(:minLng IS NULL OR r.longitude >= :minLng) AND " +
           "(:maxLng IS NULL OR r.longitude <= :maxLng)")
    List<Report> searchReports(String animalType, Boolean isResolved, 
                             Double minLat, Double maxLat, 
                             Double minLng, Double maxLng);
} 