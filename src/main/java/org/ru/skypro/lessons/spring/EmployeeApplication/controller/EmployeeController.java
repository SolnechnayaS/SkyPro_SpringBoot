package org.ru.skypro.lessons.spring.EmployeeApplication.controller;

import org.ru.skypro.lessons.spring.EmployeeApplication.model.Employee;
import org.ru.skypro.lessons.spring.EmployeeApplication.service.EmployeeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("employee/salary")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/min")
    public Optional<Employee> getMinSalary() {
        return employeeService.getMinSalary();
    }

    @GetMapping("/max")
    public Optional<Employee> getMaxSalary() {
        return employeeService.getMaxSalary();
    }

    @GetMapping("/sum")
    public Double getSumSalary() {
        return employeeService.getSumSalary();
    }

    @GetMapping("/average")
    public Double getAverageSalary() {
        return employeeService.getAverageSalary();
    }

    @GetMapping("/high")
    public List<Employee> getHighSalary() {
        return employeeService.getHighSalary();
    }

}
