package org.ru.skypro.lessons.spring.EmployeeApplication.controller;

import org.ru.skypro.lessons.spring.EmployeeApplication.dto.EmployeeDTO;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Employee;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.projections.EmployeeFullInfo;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.projections.EmployeeInfo;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.projections.EmployeeView;
import org.ru.skypro.lessons.spring.EmployeeApplication.service.EmployeeService;
import org.ru.skypro.lessons.spring.EmployeeApplication.service.ReportService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/admin/employees")
public class AdminEmployeeController {

    private final EmployeeService employeeService;

    private final ReportService reportService;

    public AdminEmployeeController(EmployeeService employeeService, ReportService reportService) {
        this.employeeService = employeeService;
        this.reportService = reportService;
    }

    @PostMapping("/generate")
    public void generateEmployees(@RequestParam("number") Integer number) {
        for (int i = 0; i < number; i++) {
            employeeService.addEmployee(employeeService.generateRandomEmployees());
        }
    }

    @PutMapping("/{id}")
    public void readEmployeeById(@PathVariable("id") Integer id, @RequestBody Employee employeeNew) {
        employeeService.addEmployee(employeeService.editEmployeeById(id, employeeNew));
    }

    @DeleteMapping("/{id}")
    public void deleteEmployeeById(@PathVariable int id) {
        employeeService.deleteEmployeeById(id);
    }


    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadFile(@RequestBody MultipartFile file) throws IOException {
        System.out.println("Файл загружен. Имя файла: " + file.getOriginalFilename() +
                " Размер файла: " + file.getSize() + " байт");
        employeeService.uploadEmployeesFromFile(file);
    }

    @PostMapping("/report")
    public void saveReportStatisticsAllDivisions() {
        reportService.saveReportStatisticsAllDivisions();
    }

    @PostMapping("/report/division")
    public void saveReportStatisticsByDivision(@RequestParam ("id") Integer divisionId) throws IOException {
        reportService.saveReportStatisticsDivision(reportService.reportStatisticsByDivision(divisionId));
    }


}
