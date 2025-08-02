package com.example.accessinsightsbackend;

import com.example.accessinsightsbackend.dto.LintFileContentDTO;
import com.example.accessinsightsbackend.dto.RecentScanFileDTO;
import com.example.accessinsightsbackend.dto.RecentScanReportDTO;
import com.example.accessinsightsbackend.model.LintFile;
import com.example.accessinsightsbackend.model.LintReport;
import com.example.accessinsightsbackend.repository.LintReportRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/lint-reports")
@Slf4j
public class LintReportController {

    @Autowired
    private LintReportRepository lintReportRepository;

    @Autowired
    private ObjectMapper objectMapper; // To convert JsonNode to String and vice-versa

    @PostMapping
    public ResponseEntity<String> receiveLintReport(@RequestBody JsonNode lintReportJson) {
        log.info("Received lint report.");

        try {
            // Create a new LintReport entity
            LintReport lintReport = new LintReport();
            // Set timestamp from our end (or parse from JSON if available and reliable)
            lintReport.setTimestamp(LocalDateTime.now());

            // Parse files array from the incoming JSON
            JsonNode filesNode = lintReportJson.get("files");
            List<LintFile> lintFiles = new ArrayList<>();

            if (filesNode != null && filesNode.isArray()) {
                for (JsonNode fileNode : filesNode) {
                    LintFile lintFile = new LintFile();
                    lintFile.setFilePath(fileNode.get("filePath").asText());
                    lintFile.setFileReportJson(objectMapper.writeValueAsString(fileNode)); // Store the whole file JSON
                    lintFile.setLintReport(lintReport); // Set the parent report
                    lintFiles.add(lintFile);
                }
            }
            lintReport.setFiles(lintFiles);

            // Save the lintReport (which will cascade save lintFiles)
            lintReportRepository.save(lintReport);

            log.info("Lint report saved successfully with ID: {}", lintReport.getId());
            return ResponseEntity.ok("Lint report received and saved successfully!");

        } catch (Exception e) {
            log.error("Error processing or saving lint report: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Error processing lint report: " + e.getMessage());
        }
    }

    @GetMapping("/recent-pagewise")
    public ResponseEntity<RecentScanReportDTO> getRecentPagewiseScan() {
        log.info("Fetching most recent page-wise scan report.");

        Optional<LintReport> latestReportOptional = lintReportRepository.findAll()
                .stream()
                .max(Comparator.comparing(LintReport::getTimestamp));

        if (latestReportOptional.isEmpty()) {
            log.info("No lint reports found.");
            return ResponseEntity.notFound().build();
        }

        LintReport latestReport = latestReportOptional.get();

        List<RecentScanFileDTO> fileDTOs = latestReport.getFiles().stream()
                .map(lintFile -> {
                    try {
                        // Convert the stored JSON string back to LintFileContentDTO
                        LintFileContentDTO fileContentDTO = objectMapper.readValue(lintFile.getFileReportJson(), LintFileContentDTO.class);
                        return new RecentScanFileDTO(lintFile.getFilePath(), fileContentDTO);
                    } catch (Exception e) {
                        log.error("Error parsing fileReportJson for file {}: {}", lintFile.getFilePath(), e.getMessage());
                        return null; // Or handle error appropriately
                    }
                })
                .filter(dto -> dto != null) // Filter out any nulls from parsing errors
                .collect(Collectors.toList());

        RecentScanReportDTO responseDTO = new RecentScanReportDTO(
                latestReport.getTimestamp(),
                fileDTOs
        );

        return ResponseEntity.ok(responseDTO);
    }
}