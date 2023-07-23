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
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class ReportServiceImpl implements ReportService {
    private static final Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);
    private final ReportRepository reportRepository;

    public ReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public String serializeReportStatisticDivision(ReportStatisticsDivision reportStatisticsDivision) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(reportStatisticsDivision);
    }

    @Override
    public ReportStatisticsDivision deserializeReportStatisticDivision(String reportStatisticDivision) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(reportStatisticDivision, ReportStatisticsDivision.class);
    }


    @Override
    public List<ReportStatisticsDivision> reportStatisticsAllDivisions() {
        logger.info("Generate a statisticals reports for all divisions");
        return reportRepository.reportStatisticsAllDivisions();
    }

    @Override
    public ReportStatisticsDivision reportStatisticsByDivision(Integer divisionId) {
        logger.info("Generate a statistical report for division with id="+divisionId);
        return reportRepository.reportStatisticsByDivision(divisionId);
    }

    @Override
    public ResponseEntity<Resource> saveReportStatisticsDivision(ReportStatisticsDivision reportStatisticsDivision) throws IOException {
        logger.info("Serialize and Write to String Report Statistics Division");
        String jsonTextReport = serializeReportStatisticDivision(reportStatisticsDivision);

        String fileName = "StatisticDivision_" + reportStatisticsDivision.getDivisionName().replace(" ", "_") + "_DT" + LocalDateTime.now() +
                ".json";
        Path path = Path.of(
                "src/main/java/org/ru/skypro/lessons/spring/EmployeeApplication",
                "/REPORTS",
                fileName);

        Files.writeString(path, jsonTextReport);

        Report report = new Report(
//                jsonTextReport,
                String.valueOf(path));

        logger.info("save report");
        reportRepository.save(report);

        Long lastReportId = reportRepository.findLastReportId();

        logger.info("Report with report_id=" + lastReportId +" saved to the database");

        Resource resource = new ByteArrayResource(jsonTextReport.getBytes(), "Отчет " + fileName);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(resource);
    }

    @Override
    public void saveReportStatisticsAllDivisions() {
        reportRepository.reportStatisticsAllDivisions()
                .forEach(reportStatisticsDivision -> {
                    try {
                        saveReportStatisticsDivision(reportStatisticsDivision);
                    } catch (IOException e) {
                        logger.error("the report is not saved to the database");
                        throw new RuntimeException("the report is not saved to the database");
                    }
                });
    }

    @Override
    public ResponseEntity<Resource> downloadReportFile(Integer reportId) throws IOException {
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
        }
        catch (Exception e)
        {
            logger.error("The report with this id does not exist or the file path is incorrect");
            throw new NoSuchFileException("The report with this id does not exist or the file path is incorrect");
        }

    }
}
