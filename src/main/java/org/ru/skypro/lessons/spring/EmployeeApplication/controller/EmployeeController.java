package org.ru.skypro.lessons.spring.EmployeeApplication.controller;

import org.ru.skypro.lessons.spring.EmployeeApplication.model.Employee;
import org.ru.skypro.lessons.spring.EmployeeApplication.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public Map<Integer, Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/")
    public Map<Integer, Employee> readAllEmployee() {
        return employeeService.readAllEmployee();
    }

    @GetMapping("/salary/min")
    public Optional<Employee> getMinSalary() {
        return employeeService.getMinSalary();
    }

    @GetMapping("/salary/max")
    public Optional<Employee> getMaxSalary() {
        return employeeService.getMaxSalary();
    }

    @GetMapping("/salary/sum")
    public Double getSumSalary() {
        return employeeService.getSumSalary();
    }

    @GetMapping("/salary/average")
    public Double getAverageSalary() {
        return employeeService.getAverageSalary();
    }

    @GetMapping("/salary/high")
    public List<Employee> getHighSalary() {
        return employeeService.getHighSalary();
    }

    @PostMapping("/generate")
    public void generateEmployees(@RequestParam("number") Integer number) {
        employeeService.generateEmployees(number);
    }

    @PutMapping("/{id}")
    public void editEmployeeById (@PathVariable int id, @RequestBody Double indexation) {
        employeeService.editEmployeeById(id,indexation);
    }

    @GetMapping("/{id}")
    public Employee readEmployeeById(@PathVariable int id) {
        return employeeService.readEmployeeById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployeeById(@PathVariable int id) {
        employeeService.deleteEmployeeById(id);
    }

    @GetMapping("/salary/HigherThan")
    public List<Employee> salaryHigherThan(@RequestParam("salary") Integer salary) {
        return employeeService.salaryHigherThan(salary);
    }

}
