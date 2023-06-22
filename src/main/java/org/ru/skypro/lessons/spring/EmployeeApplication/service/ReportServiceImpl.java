package org.ru.skypro.lessons.spring.EmployeeApplication.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Report;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.projections.ReportStatisticsDivision;
import org.ru.skypro.lessons.spring.EmployeeApplication.repository.DivisionRepository;
import org.ru.skypro.lessons.spring.EmployeeApplication.repository.EmployeeRepository;
import org.ru.skypro.lessons.spring.EmployeeApplication.repository.PositionRepository;
import org.ru.skypro.lessons.spring.EmployeeApplication.repository.ReportRepository;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class ReportServiceImpl implements ReportService {
    private final EmployeeRepository employeeRepository;

    private final PositionRepository positionRepository;

    private final DivisionRepository divisionRepository;

    private final ReportRepository reportRepository;


    public ReportServiceImpl(EmployeeRepository employeeRepository, PositionRepository positionRepository, DivisionRepository divisionRepository, ReportRepository reportRepository) {
        this.employeeRepository = employeeRepository;
        this.positionRepository = positionRepository;
        this.divisionRepository = divisionRepository;
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
        return reportRepository.reportStatisticsAllDivisions();
    }

    @Override
    public ReportStatisticsDivision reportStatisticsByDivision(Integer divisionId) {
        return reportRepository.reportStatisticsByDivision(divisionId);
    }

    @Override
    public ResponseEntity<Resource> saveReportStatisticsDivision(ReportStatisticsDivision reportStatisticsDivision) throws IOException {
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

        reportRepository.save(report);
        Long lastReportId = reportRepository.findLastReportId();

        System.out.println("Отчет с report_id=" + lastReportId +
                " сохранен в базу данных");

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
                        throw new RuntimeException(e);
                    }
                });
    }

    @Override
    public ResponseEntity<Resource> downloadReportFile(Integer reportId) throws IOException {
        String filePath = reportRepository.findFilePathByReportId(reportId);

        Path path = Path.of(filePath);

        System.out.println("Файл существует=" + Files.exists(path) + ". Имя файла " + path.getFileName());

        Resource resource = new ByteArrayResource(Files.readAllBytes(path));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + path.getFileName() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(resource);

    }
}
