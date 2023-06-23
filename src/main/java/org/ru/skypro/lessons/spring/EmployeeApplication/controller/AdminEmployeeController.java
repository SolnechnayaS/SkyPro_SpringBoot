package org.ru.skypro.lessons.spring.EmployeeApplication.controller;

import org.ru.skypro.lessons.spring.EmployeeApplication.model.Employee;
import org.ru.skypro.lessons.spring.EmployeeApplication.security.AuthUser;
import org.ru.skypro.lessons.spring.EmployeeApplication.service.EmployeeService;
import org.ru.skypro.lessons.spring.EmployeeApplication.service.ReportService;
import org.ru.skypro.lessons.spring.EmployeeApplication.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/admin")
public class AdminEmployeeController {

    private final EmployeeService employeeService;

    private final ReportService reportService;

    private final UserService userService;

    public AdminEmployeeController(EmployeeService employeeService, ReportService reportService, UserService userService) {
        this.employeeService = employeeService;
        this.reportService = reportService;
        this.userService = userService;
    }

    @PostMapping("/employees/generate")
    public void generateEmployees(@RequestParam("number") Integer number) {
        for (int i = 0; i < number; i++) {
            employeeService.addEmployee(employeeService.generateRandomEmployees());
        }
    }

    @PutMapping("/employees/{id}")
    public void readEmployeeById(@PathVariable("id") Integer id, @RequestBody Employee employeeNew) {
        employeeService.addEmployee(employeeService.editEmployeeById(id, employeeNew));
    }

    @DeleteMapping("/employees/{id}")
    public void deleteEmployeeById(@PathVariable int id) {
        employeeService.deleteEmployeeById(id);
    }


    @PostMapping(value = "/employees/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadFile(@RequestBody MultipartFile file) throws IOException {
        System.out.println("Файл загружен. Имя файла: " + file.getOriginalFilename() +
                " Размер файла: " + file.getSize() + " байт");
        employeeService.uploadEmployeesFromFile(file);
    }

    @PostMapping("/employees/report")
    public void saveReportStatisticsAllDivisions() {
        reportService.saveReportStatisticsAllDivisions();
    }

    @PostMapping("/employees/report/division")
    public void saveReportStatisticsByDivision(@RequestParam ("id") Integer divisionId) throws IOException {
        reportService.saveReportStatisticsDivision(reportService.reportStatisticsByDivision(divisionId));
    }

    @PostMapping("/users/create")
    public void createNewUser(@RequestBody AuthUser user) {
        userService.addUser(user);
    }

}
