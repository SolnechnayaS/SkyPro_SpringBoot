package org.ru.skypro.lessons.spring.EmployeeApplication.service;

import org.ru.skypro.lessons.spring.EmployeeApplication.dto.EmployeeDTO;
import org.ru.skypro.lessons.spring.EmployeeApplication.exception.IncorrectEmployeeIdException;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Employee;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.projections.EmployeeFullInfo;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    List<EmployeeDTO> allEmployeesToEmployeesDTO(List<Employee> employees);

    List<Employee> findAllEmployees();

    Double getSumSalary ();
    Double getAverageSalary();
    List<Employee> getHighestSalary () throws IncorrectEmployeeIdException;
    List<Employee> salaryHigherThan(Integer salary);
    Employee generateRandomEmployees();
    EmployeeFullInfo getEmployeeByIdFullInfo(int id) throws IncorrectEmployeeIdException;
    Optional<Employee> getEmployeeById(int id);
    void deleteEmployeeById (int id);
    void addEmployee(Employee employee);

    List<Employee> getEmployeeWithPaging(int pageIndex, int unitPerPage);

    List<Employee> getAllEmployeesByPosition(Integer position);
}
