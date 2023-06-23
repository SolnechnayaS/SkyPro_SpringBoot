package org.ru.skypro.lessons.spring.EmployeeApplication.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ru.skypro.lessons.spring.EmployeeApplication.dto.EmployeeDTO;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Employee;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.projections.EmployeeFullInfo;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.projections.EmployeeInfo;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.projections.EmployeeView;
import org.ru.skypro.lessons.spring.EmployeeApplication.service.EmployeeService;
import org.ru.skypro.lessons.spring.EmployeeApplication.service.ReportService;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    private final ReportService reportService;

    public EmployeeController(EmployeeService employeeService, ReportService reportService) {
        this.employeeService = employeeService;
        this.reportService = reportService;
    }

    @GetMapping("/")
    public List<EmployeeDTO> findAllEmployees() {
        return employeeService.allEmployeesToEmployeesDTO(employeeService.findAllEmployees());
    }

    @GetMapping("/{id}")
    public EmployeeDTO readEmployeeById(@PathVariable Integer id) {
        return employeeService.getEmployeeById(id);
    }

    @GetMapping("/salary/HigherThan")
    public List<EmployeeFullInfo> salaryHigherThan(@RequestParam(value = "salary", defaultValue = "0") Integer salary) {
        return employeeService.salaryHigherThan(salary);
    }

    @GetMapping("/withHighestSalary")
    public List<EmployeeFullInfo> getEmployeeFullInfoWithMaxSalary() {
        return employeeService.getEmployeeFullInfoWithMaxSalary();
    }

    @GetMapping
    public List<EmployeeFullInfo> getAllEmployeesByPosition(@RequestParam(value = "position", required = false) Long positionId) {
        return employeeService.getEmployeeFullInfoByPosition(positionId);
    }

    @GetMapping("/{id}/fullInfo")
    public EmployeeFullInfo readEmployeeByIdFullInfo(@PathVariable Integer id) {
        return employeeService.getEmployeeFullInfoById(id);
    }

    @GetMapping("/page")
    public List<EmployeeFullInfo> getEmployeeWithPaging(@RequestParam(value = "page", defaultValue = "1") Integer pageIndex, @RequestParam(value = "size", defaultValue = "10", required = false) int unitPerPage) {
        return employeeService.getEmployeeFullInfoWithPaging(pageIndex, unitPerPage);
    }

    @GetMapping("/view")
    public List<EmployeeView> findAllEmployeesView() {
        return employeeService.findAllEmployeesView();
    }

    @GetMapping("/fullInfo")
    public List<EmployeeFullInfo> findAllEmployeesFullInfo() {
        return employeeService.findAllEmployeesFullInfo();
    }

    @GetMapping("/info")
    public List<EmployeeInfo> findAllEmployeeInfo() {
        return employeeService.findAllEmployeesInfo();
    }

    @GetMapping("/salary/max")
    public Integer getMaxSalary() {
        return employeeService.getMaxSalary();
    }

    @GetMapping("/report/{id}")
    public ResponseEntity<Resource> downloadReportFile(@PathVariable ("id") Integer reportId) throws IOException {
        return reportService.downloadReportFile(reportId);
    }

}
