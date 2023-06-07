package org.ru.skypro.lessons.spring.EmployeeApplication.service;

import org.ru.skypro.lessons.spring.EmployeeApplication.model.Employee;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface EmployeeService {

    Map<Integer,Employee> getAllEmployees();

    Optional<Employee> getMinSalary ();
    Optional<Employee> getMaxSalary ();
    Double getSumSalary ();
    Double getAverageSalary();
    List<Employee> getHighSalary ();

    void generateEmployees (Integer number);

    void editEmployeeById (int id, double indexation);

    Employee readEmployeeById (int id);

     Map <Integer, Employee> readAllEmployee();

    void deleteEmployeeById (int id);

    List<Employee> salaryHigherThan(Integer salary);

}
