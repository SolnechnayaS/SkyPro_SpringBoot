package org.ru.skypro.lessons.spring.EmployeeApplication.repository;

import org.ru.skypro.lessons.spring.EmployeeApplication.model.Employee;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Component
@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository{

    private final List<Employee> employeeList = List.of(
            new Employee("Katya", 190_000),
            new Employee("Dima", 150_000),
            new Employee("Oleg", 150_000),
            new Employee("Vika", 165_000));

    @Override
    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employeeList);
    }
}
