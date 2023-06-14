package org.ru.skypro.lessons.spring.EmployeeApplication.controller;

import org.ru.skypro.lessons.spring.EmployeeApplication.dto.EmployeeDTO;
import org.ru.skypro.lessons.spring.EmployeeApplication.exception.IncorrectEmployeeIdException;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Employee;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Position;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.projections.EmployeeFullInfo;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.projections.EmployeeInfo;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.projections.EmployeeView;
import org.ru.skypro.lessons.spring.EmployeeApplication.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController<employeeNew> {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/generate")
    public void generateEmployees(@RequestParam("number") Integer number) {
        for (int i = 0; i < number; i++) {
            employeeService.addEmployee(employeeService.generateRandomEmployees());
        }
    }
    @PutMapping("/{id}")
    public void readEmployeeById(@PathVariable ("id") Integer id, @RequestBody Employee employeeNew) {
        employeeService.addEmployee(employeeService.editEmployeeById(id, employeeNew));
    }

    @GetMapping("/")
    public List<EmployeeDTO> findAllEmployees() {
        return employeeService.allEmployeesToEmployeesDTO(employeeService.findAllEmployees());
    }

    @GetMapping("/{id}")
    public EmployeeDTO readEmployeeById(@PathVariable Integer id) {
        return employeeService.getEmployeeById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployeeById(@PathVariable int id) {
        employeeService.deleteEmployeeById(id);
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
    public List<EmployeeFullInfo> getAllEmployeesByPosition(@RequestParam(value = "position", required = false) Integer positionId) {
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



}
