package org.ru.skypro.lessons.spring.EmployeeApplication.controller;

import org.ru.skypro.lessons.spring.EmployeeApplication.dto.EmployeeDTO;
//import org.ru.skypro.lessons.spring.EmployeeApplication.security.AuthUser;
import org.ru.skypro.lessons.spring.EmployeeApplication.service.EmployeeService;
import org.ru.skypro.lessons.spring.EmployeeApplication.service.ReportService;
//import org.ru.skypro.lessons.spring.EmployeeApplication.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminEmployeeController {
    private static final Logger logger = LoggerFactory.getLogger(AdminEmployeeController.class);

    private final EmployeeService employeeService;
    private final ReportService reportService;

    public AdminEmployeeController(EmployeeService employeeService, ReportService reportService) {
        this.employeeService = employeeService;
        this.reportService = reportService;
    }

    @GetMapping("/")
    public List<EmployeeDTO> findAllEmployees() {
        return employeeService.allEmployeesToEmployeesDTO(employeeService.findAllEmployees());
    }

    @PostMapping("/employees/generate")
    public void generateEmployees(@RequestParam("number") Integer number) throws IOException {
        List<EmployeeDTO> listRandomEmployees = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            listRandomEmployees.add(employeeService.generateRandomEmployees());
        }
        employeeService.uploadEmployeesFromListEmployeeDTO(listRandomEmployees);
    }

    @PutMapping("/employees/{id}")
    public void readEmployeeById(@PathVariable("id") Long id, @RequestBody EmployeeDTO employeeNew) {
        employeeService.readEmployeeById(id, employeeNew);
    }

    @DeleteMapping("/employees/{id}")
    public void deleteEmployeeById(@PathVariable Long id) {
        employeeService.deleteEmployeeById(id);
    }


    @PostMapping(value = "/employees/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        logger.info("Файл загружен. Имя файла: " + file.getOriginalFilename() +
                " Размер файла: " + file.getSize() + " байт");
        employeeService.uploadEmployeesFromListEmployeeDTO(employeeService.getEmployeesFromFile(file));
    }

    @GetMapping("/employees/report")
    public void saveReportStatisticsAllDivisions() throws IOException {
        reportService.saveReportStatisticsAllDivisions(reportService.reportStatisticsAllDivisions());
    }

    @GetMapping("/employees/report/division")
    public void saveReportStatisticsByDivision(@RequestParam("id") Long divisionId) throws IOException {
        reportService.saveReportStatisticsDivision(reportService.reportStatisticsByDivision(divisionId));
    }

}
