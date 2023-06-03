package org.ru.skypro.lessons.spring.EmployeeApplication.service;

import org.ru.skypro.lessons.spring.EmployeeApplication.model.Employee;
import org.ru.skypro.lessons.spring.EmployeeApplication.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    public static class SalaryEmployeeComparator implements Comparator<Employee> {
        @Override
        public int compare(Employee o1, Employee o2) {
            return o1.getSalary() - o2.getSalary();
        }
    }

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.getAllEmployees();
    }

//    public Employee getMinSalary() {
//        List<Employee> sortedListEmployees = getAllEmployees().stream().sorted(new SalaryEmployeeComparator()).toList();
//        return sortedListEmployees.get(0);
//    }
//
//    public Employee getMaxSalary() {
//        List<Employee> sortedListEmployees = getAllEmployees().stream().sorted((new SalaryEmployeeComparator()).reversed()).toList();
//        return sortedListEmployees.get(0);
//    }

    public Optional<Employee> getMinSalary() {
        return getAllEmployees()
                .stream()
                .min(new SalaryEmployeeComparator());
    }

    public Optional<Employee> getMaxSalary() {
        return getAllEmployees()
                .stream()
                .max(new SalaryEmployeeComparator());
    }

    public Double getSumSalary() {
        return getAllEmployees()
                .stream()
                .mapToDouble(Employee::getSalary)
                .sum();
    }
    public Double getAverageSalary() {
        return getSumSalary()/(getAllEmployees().size());
    }

    public List<Employee> getHighSalary() {
        return getAllEmployees()
                .stream()
                .filter(employee -> employee.getSalary()>(getAverageSalary()))
                .toList();
    }
}
