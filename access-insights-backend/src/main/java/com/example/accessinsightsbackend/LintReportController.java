package com.example.accessinsightsbackend;

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

import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/lint-reports")
public class LintReportController {

    @Autowired
    private LintReportRepository lintReportRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping
    public ResponseEntity<String> receiveLintReport(@RequestBody JsonNode lintReportJson) {
        System.out.println("Received lint report." + lintReportJson.toString());

        try {
            LintReport lintReport = new LintReport();
            lintReport.setTimestamp(LocalDateTime.now());

            List<LintFile> lintFiles = new ArrayList<>();

            if (lintReportJson != null && lintReportJson.isArray()) {
                System.out.println("Files node is an array with " + lintReportJson.size() + " elements.");
                for (JsonNode fileNode : lintReportJson) {
                    LintFile lintFile = new LintFile();
                    lintFile.setFilePath(fileNode.get("filePath").asText());
                    lintFile.setFileReportJson(objectMapper.writeValueAsString(fileNode));
                    lintFile.setLintReport(lintReport);
                    lintFiles.add(lintFile);
                }
            }
            lintReport.setFiles(lintFiles);

            lintReportRepository.save(lintReport);

            System.out.println("Lint report saved successfully with ID: " + lintReport.getId());
            return ResponseEntity.ok("Lint report received and saved successfully!");

        } catch (Exception e) {
            System.err.println("Error processing or saving lint report: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error processing lint report: " + e.getMessage());
        }
    }

    @GetMapping("/recent-pagewise")
    public ResponseEntity<RecentScanReportDTO> getRecentPagewiseScan() {
        System.out.println("Fetching most recent page-wise scan report.");

        Optional<LintReport> latestReportOptional = lintReportRepository.findAll()
                .stream()
                .max(Comparator.comparing(LintReport::getTimestamp));

        if (latestReportOptional.isEmpty()) {
            System.out.println("No lint reports found.");
            return ResponseEntity.notFound().build();
        }

        LintReport latestReport = latestReportOptional.get();

        List<RecentScanFileDTO> fileDTOs = latestReport.getFiles().stream()
                .map(lintFile -> {
                    try {
                        // LintFileContentDTO fileContentDTO = objectMapper.readValue(lintFile.getFileReportJson(), LintFileContentDTO.class);
                        // return new RecentScanFileDTO(lintFile.getFilePath(), fileContentDTO);
                        // Directly map fields to RecentScanFileDTO
                        JsonNode fileContentJson = objectMapper.readTree(lintFile.getFileReportJson());
                        return new RecentScanFileDTO(
                                lintFile.getFilePath(),
                                objectMapper.readValue(fileContentJson.get("messages").toString(), new com.fasterxml.jackson.core.type.TypeReference<List<com.example.accessinsightsbackend.dto.LintMessageDTO>>() {}),
                                fileContentJson.get("errorCount").asInt(),
                                fileContentJson.get("warningCount").asInt(),
                                fileContentJson.get("fixableErrorCount").asInt(),
                                fileContentJson.get("fixableWarningCount").asInt(),
                                fileContentJson.has("source") ? fileContentJson.get("source").asText() : null
                        );
                    } catch (Exception e) {
                        System.err.println("Error parsing fileReportJson for file " + lintFile.getFilePath() + ": " + e.getMessage());
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(dto -> dto != null)
                .collect(Collectors.toList());

        RecentScanReportDTO responseDTO = new RecentScanReportDTO(
                latestReport.getTimestamp(),
                fileDTOs
        );

        return ResponseEntity.ok(responseDTO);
    }
}
