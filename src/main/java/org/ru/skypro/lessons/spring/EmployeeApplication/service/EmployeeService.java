package org.ru.skypro.lessons.spring.EmployeeApplication.service;

import org.ru.skypro.lessons.spring.EmployeeApplication.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    List<Employee> getAllEmployees();

    Optional<Employee> getMinSalary ();
    Optional<Employee> getMaxSalary ();
    Double getSumSalary ();
    Double getAverageSalary();
    List<Employee> getHighSalary ();

}
