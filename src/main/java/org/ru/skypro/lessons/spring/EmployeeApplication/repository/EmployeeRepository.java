package org.ru.skypro.lessons.spring.EmployeeApplication.repository;

import org.ru.skypro.lessons.spring.EmployeeApplication.model.Employee;

import java.util.List;
import java.util.Map;

public interface EmployeeRepository {
    Map<Integer,Employee> getAllEmployees();

}
