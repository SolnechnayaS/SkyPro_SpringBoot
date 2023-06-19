package org.ru.skypro.lessons.spring.EmployeeApplication.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.ru.skypro.lessons.spring.EmployeeApplication.dto.EmployeeDTO;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Employee;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.projections.ReportStatisticsDivision;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

public interface ReportService {


    String serializeReportStatisticDivision(ReportStatisticsDivision reportStatisticsDivision) throws JsonProcessingException;

    ReportStatisticsDivision deserializeReportStatisticDivision(String reportStatisticDivision) throws JsonProcessingException;

    ResponseEntity<Resource> saveReportStatisticsDivision(ReportStatisticsDivision reportStatisticsDivision) throws IOException;

    List<ReportStatisticsDivision> reportStatisticsAllDivisions();

    ReportStatisticsDivision reportStatisticsByDivision(Integer divisionId);

    void saveReportStatisticsAllDivisions();

    ResponseEntity<Resource> downloadReportFile(Integer reportId) throws IOException;
}
