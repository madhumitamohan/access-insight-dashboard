package com.example.accessinsightsbackend.dto;

import java.time.LocalDateTime;
import java.util.List;

public class RecentScanReportDTO {
    private LocalDateTime timestamp;
    private List<RecentScanFileDTO> files;

    public RecentScanReportDTO() {
    }

    public RecentScanReportDTO(LocalDateTime timestamp, List<RecentScanFileDTO> files) {
        this.timestamp = timestamp;
        this.files = files;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public List<RecentScanFileDTO> getFiles() {
        return files;
    }

    public void setFiles(List<RecentScanFileDTO> files) {
        this.files = files;
    }
}