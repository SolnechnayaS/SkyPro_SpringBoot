package org.ru.skypro.lessons.spring.EmployeeApplication.controller;

import org.ru.skypro.lessons.spring.EmployeeApplication.dto.EmployeeDTO;
import org.ru.skypro.lessons.spring.EmployeeApplication.exception.IncorrectEmployeeIdException;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Employee;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.projections.EmployeeFullInfo;
import org.ru.skypro.lessons.spring.EmployeeApplication.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    public List<EmployeeDTO> findAllEmployees() {
        return employeeService.allEmployeesToEmployeesDTO(employeeService.findAllEmployees());
    }

    @GetMapping("/withHighestSalary")
    public List<EmployeeDTO> getHighestSalary() throws IncorrectEmployeeIdException {
        return employeeService.allEmployeesToEmployeesDTO(employeeService.getHighestSalary());
    }

    @GetMapping
    public List<EmployeeFullInfo> getAllEmployeesByPosition(@RequestParam(value = "position", required=false) Integer positionId) {
        return employeeService.getAllEmployeesByPosition(positionId)
                .stream()
                .map(EmployeeFullInfo::fromEmployee)
                .toList();
    }

    @GetMapping("/page")
    public List<EmployeeFullInfo> getEmployeeWithPaging(@RequestParam(value = "page", defaultValue = "1") Integer pageIndex, @RequestParam(value = "size", defaultValue = "10", required=false) int unitPerPage) {
        return employeeService.getEmployeeWithPaging(pageIndex,unitPerPage)
                .stream()
                .map(EmployeeFullInfo::fromEmployee)
                .toList();
    }

    @PostMapping("/generate")
    public void generateEmployees(@RequestParam("number") Integer number) {
        for (int i = 0; i < number; i++) {
            employeeService.addEmployee(employeeService.generateRandomEmployees());
        }

    }

    @PostMapping("/")
    public void addEmployees(@RequestBody Employee employee) {
        employeeService.addEmployee(employee);
    }

    @GetMapping("/{id}")
    public Optional<Employee> readEmployeeById(@PathVariable int id) {
        System.out.println(employeeService.getEmployeeById(id));
        return employeeService.getEmployeeById(id);
    }

    @GetMapping("/{id}/fullInfo")
    public EmployeeFullInfo readEmployeeByIdFullInfo(@PathVariable int id) throws IncorrectEmployeeIdException {
        return employeeService.getEmployeeByIdFullInfo(id);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployeeById(@PathVariable int id) {
        employeeService.deleteEmployeeById(id);
    }

    @GetMapping("/salary/HigherThan")
    public List<EmployeeDTO> salaryHigherThan(@RequestParam("salary") Integer salary) {
        return employeeService.allEmployeesToEmployeesDTO(employeeService.salaryHigherThan(salary));
    }

}
