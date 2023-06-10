package org.ru.skypro.lessons.spring.EmployeeApplication.service;

import org.ru.skypro.lessons.spring.EmployeeApplication.dto.EmployeeDTO;
import org.ru.skypro.lessons.spring.EmployeeApplication.exception.IncorrectEmployeeIdException;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Employee;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Position;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.projections.EmployeeFullInfo;
import org.ru.skypro.lessons.spring.EmployeeApplication.repository.EmployeeRepository;
import org.ru.skypro.lessons.spring.EmployeeApplication.repository.NameGenerator;
import org.ru.skypro.lessons.spring.EmployeeApplication.repository.PositionRepository;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    private final PositionRepository positionRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, PositionRepository positionRepository) {
        this.employeeRepository = employeeRepository;
        this.positionRepository = positionRepository;
    }


    public Double getSumSalary() {
        return findAllEmployees()
                .stream()
                .mapToDouble(Employee::getSalary)
                .sum();
    }

    public Double getAverageSalary() {
        return getSumSalary() / (findAllEmployees().size());
    }

    public List<Employee> getHighestSalary() throws IncorrectEmployeeIdException {
        Employee employeeMaxSalary = findAllEmployees()
                .stream()
                .max(Comparator.comparingDouble(Employee::getSalary)).orElseThrow(() -> new IncorrectEmployeeIdException("Пользователь не найден"));
        Integer maxSalary = employeeMaxSalary.getSalary();

        return findAllEmployees()
                .stream()
                .filter(employeeDTO -> Objects.equals(employeeDTO.getSalary(), maxSalary))
                .toList();
    }
    public List<Employee> salaryHigherThan(Integer salary) {
        return findAllEmployees()
                .stream()
                .filter(employee -> employee.getSalary() > (salary))
                .toList();
    }

    @Override
    public List<EmployeeDTO> allEmployeesToEmployeesDTO(List<Employee> employees) {
        return employees
                .stream()
                .map(EmployeeDTO::fromEmployee)
                .toList();
    }

    @Override
    public List<Employee> findAllEmployees() {
        List<Employee> employeeList = new ArrayList<>();
        Iterator<Employee> employeeIterator = employeeRepository
                .findAll()
                .iterator();
        employeeIterator.forEachRemaining(employeeList::add);
        return employeeList;
    }

    public Employee generateRandomEmployees() {
        int randomSalary = (int) (Math.random() * 100000 + 50000);
        int randomPositionId = (int) Math.round(Math.random() * 24) + 1;
        Position randomPosition = positionRepository.findById(randomPositionId).orElseThrow();
        Employee randomEmployee = new Employee();
        randomEmployee.setName(NameGenerator.randomName());
        randomEmployee.setSalary(randomSalary);
        randomEmployee.setPosition(randomPosition);
        return randomEmployee;
    }

    @Override
    public Optional<Employee> getEmployeeById(int id) {
        return employeeRepository.findById(id);
    }

    @Override
    public EmployeeFullInfo getEmployeeByIdFullInfo(int id) throws IncorrectEmployeeIdException {
        return EmployeeFullInfo.fromEmployee(employeeRepository.findById(id).orElseThrow(() -> new IncorrectEmployeeIdException("Пользователь не найден")));
    }

    @Override
    public void deleteEmployeeById(int id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public void addEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getEmployeeWithPaging(int pageIndex, int unitPerPage) {
        PageRequest employeeOfConcretePage = PageRequest.of(pageIndex, unitPerPage);
        Page<Employee> page = employeeRepository.findAll(employeeOfConcretePage);

        return page.stream()
                .toList();
    }

    @Override
    public List<Employee> getAllEmployeesByPosition(Integer position) {
        List<Employee> employeeListByPosition = new ArrayList<>();
        if (position == null) {
            employeeListByPosition = findAllEmployees();
        } else {
            employeeListByPosition = findAllEmployees()
                    .stream()
                    .filter(employee -> employee.getPosition().getPositionId() == position)
                    .toList();
        }
        return employeeListByPosition;
    }

}
