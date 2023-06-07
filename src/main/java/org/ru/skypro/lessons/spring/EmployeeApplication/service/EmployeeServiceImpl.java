package org.ru.skypro.lessons.spring.EmployeeApplication.service;

import org.ru.skypro.lessons.spring.EmployeeApplication.model.Employee;
import org.ru.skypro.lessons.spring.EmployeeApplication.repository.EmployeeRepository;
import org.ru.skypro.lessons.spring.EmployeeApplication.repository.NameGenerator;
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

    public Map<Integer, Employee> getAllEmployees() {
        return employeeRepository.getAllEmployees();
    }

    public Optional<Employee> getMinSalary() {
        return getAllEmployees().values()
                .stream()
                .min(new SalaryEmployeeComparator());
    }

    public Optional<Employee> getMaxSalary() {
        return getAllEmployees().values()
                .stream()
                .max(new SalaryEmployeeComparator());
    }

    public Double getSumSalary() {
        return getAllEmployees().values()
                .stream()
                .mapToDouble(Employee::getSalary)
                .sum();
    }

    public Double getAverageSalary() {
        return getSumSalary() / (getAllEmployees().size());
    }

    public List<Employee> getHighSalary() {
        return getAllEmployees().values()
                .stream()
                .filter(employee -> employee.getSalary() > (getAverageSalary()))
                .toList();
    }


    public void generateEmployees(Integer number) {
        for (int i = 0; i < number; i++) {
            int randomSalary = (int) (Math.random() * 100000 + 50000);
            Employee randomEmployee = new Employee(NameGenerator.randomName(), randomSalary);
            employeeRepository.getAllEmployees().put(randomEmployee.getId(), randomEmployee);
        }

    }

    public void editEmployeeById(int id, double indexation) {
        Employee employee = readEmployeeById(id);
        employee.setSalary((int) (employee.getSalary() * indexation));
    }

    public Employee readEmployeeById(int id) {
        return employeeRepository.getAllEmployees().get(id);
    }

    public Map<Integer, Employee> readAllEmployee() {
        return employeeRepository.getAllEmployees();
    }

    public void deleteEmployeeById(int id) {
        employeeRepository.getAllEmployees().remove(id);
    }

    public List<Employee> salaryHigherThan(Integer salary) {
        return employeeRepository.getAllEmployees()
                .values()
                .stream()
                .filter(employee -> employee.getSalary() > (salary))
                .toList();
    }
}
