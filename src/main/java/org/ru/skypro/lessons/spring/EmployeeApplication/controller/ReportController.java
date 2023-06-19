package org.ru.skypro.lessons.spring.EmployeeApplication.controller;

import org.ru.skypro.lessons.spring.EmployeeApplication.dto.EmployeeDTO;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Employee;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.projections.ReportStatisticsDivision;
import org.ru.skypro.lessons.spring.EmployeeApplication.service.EmployeeService;
import org.ru.skypro.lessons.spring.EmployeeApplication.service.ReportService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;
    private final EmployeeService employeeService;

    public ReportController(ReportService reportService, EmployeeService employeeService) {
        this.reportService = reportService;
        this.employeeService = employeeService;
    }


    @GetMapping("/")
    public void saveReportStatisticsAllDivisions() {
         reportService.saveReportStatisticsAllDivisions();
    }

        @GetMapping("/division")
    public void saveReportStatisticsByDivision(@RequestParam ("id") Integer divisionId) throws IOException {
         reportService.saveReportStatisticsDivision(reportService.reportStatisticsByDivision(divisionId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> downloadReportFile(@PathVariable ("id") Integer reportId) throws IOException {
        return reportService.downloadReportFile(reportId);
    }

}
