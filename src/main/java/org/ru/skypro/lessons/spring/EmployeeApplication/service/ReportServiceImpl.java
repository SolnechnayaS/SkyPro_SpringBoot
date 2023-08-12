package org.ru.skypro.lessons.spring.EmployeeApplication.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Report;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.projections.ReportStatisticsDivision;
import org.ru.skypro.lessons.spring.EmployeeApplication.repository.ReportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.ru.skypro.lessons.spring.EmployeeApplication.model.projections.ReportStatisticsDivision.serializeReportStatisticDivision;


@Service
public class ReportServiceImpl implements ReportService {
    private static final Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);
    private final ReportRepository reportRepository;

    public ReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public List<ReportStatisticsDivision> reportStatisticsAllDivisions() {
        logger.info("Generate a statisticals reports for all divisions");
        return reportRepository.reportStatisticsAllDivisions();
    }

    @Override
    public ReportStatisticsDivision reportStatisticsByDivision(Long divisionId) {
        logger.info("Generate a statistical report for division with id=" + divisionId);
        return reportRepository.reportStatisticsByDivision(divisionId);
    }

    private final LocalDateTime localDateTime = LocalDate.now().atStartOfDay();
    private final String fileExtension = ".json";
    private final String path =
            "src/main/java/org/ru/skypro/lessons/spring/EmployeeApplication" +
            "/REPORTS";

    private Report newReport(LocalDateTime localDateTime, String divisionName, String path, String fileExtension) {
        String fileName = "StatisticDivision_" + divisionName + "_DT" + localDateTime + fileExtension;
        Path fullPath = Path.of(path,
                fileName);
        return new Report(String.valueOf(fullPath));
    }

    @Override
    public ResponseEntity<Resource> saveReportStatisticsDivision(ReportStatisticsDivision reportStatisticsDivision) throws IOException {
        logger.info("Serialize and Write to String Report Statistics Division");

        String jsonText = serializeReportStatisticDivision(reportStatisticsDivision);

        Report newReport = newReport(localDateTime, reportStatisticsDivision.getDivisionName(), path, fileExtension);
        reportRepository.save(newReport);
        logger.info("Report saved to the database");

        Files.writeString(Path.of(newReport.getFilePath()),jsonText);
        logger.info("save report");

        Resource resource = new ByteArrayResource(jsonText.getBytes());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + Path.of(newReport.getFilePath()).getFileName() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(resource);
    }

    @Override
    public ResponseEntity<Resource> saveReportStatisticsAllDivisions(List<ReportStatisticsDivision> reportStatisticsAllDivision) throws IOException {
        logger.info("Serialize and Write to String Report Statistics Division");

        List <String> jsonText = reportStatisticsAllDivision
                .stream()
                .map(ReportStatisticsDivision -> {
                    try {
                        return serializeReportStatisticDivision(ReportStatisticsDivision);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();

        Report newReport = newReport(localDateTime, "_ALL_", path, fileExtension);
        reportRepository.save(newReport);

        logger.info("Report saved to the database");

        Files.writeString(Path.of(newReport.getFilePath()),String.join(",", jsonText));
        logger.info("save report");

        Resource resource = new ByteArrayResource(String.join(",", jsonText).getBytes());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + Path.of(newReport.getFilePath()).getFileName() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(resource);
    }

    @Override
    public ResponseEntity<Resource> downloadReportFile(Long reportId) throws IOException {
        try {
            logger.info("find File Path By Report Id");
            String filePath = reportRepository.findFilePathByReportId(reportId);

            Path path = Path.of(filePath);
            logger.info("The file exists=" + Files.exists(path) + ". File name=" + path.getFileName());

            Resource resource = new ByteArrayResource(Files.readAllBytes(path));

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + path.getFileName() + "\"")
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(resource);

        } catch (Exception e) {
            logger.error("The report with this id does not exist or the file path is incorrect");
            throw new NoSuchFileException("The report with this id does not exist or the file path is incorrect");
        }

    }
}
