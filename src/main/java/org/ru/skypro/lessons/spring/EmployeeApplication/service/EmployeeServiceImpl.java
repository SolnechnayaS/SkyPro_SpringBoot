package org.ru.skypro.lessons.spring.EmployeeApplication.service;

import org.ru.skypro.lessons.spring.EmployeeApplication.dto.EmployeeDTO;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Division;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Employee;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Position;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.projections.EmployeeFullInfo;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.projections.EmployeeInfo;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.projections.EmployeeView;
import org.ru.skypro.lessons.spring.EmployeeApplication.repository.DivisionRepository;
import org.ru.skypro.lessons.spring.EmployeeApplication.repository.EmployeeRepository;
import org.ru.skypro.lessons.spring.EmployeeApplication.repository.NameGenerator;
import org.ru.skypro.lessons.spring.EmployeeApplication.repository.PositionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    private final PositionRepository positionRepository;

    private final DivisionRepository divisionRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, PositionRepository positionRepository, DivisionRepository divisionRepository) {
        this.employeeRepository = employeeRepository;
        this.positionRepository = positionRepository;
        this.divisionRepository = divisionRepository;
    }

    @Override
    public List<Employee> findAllEmployees() {
        return employeeRepository.findAllEmployee();
    }

    @Override
    public List<EmployeeView> findAllEmployeesView() {
        return employeeRepository.findAllEmployeeView();
    }

    @Override
    public List<EmployeeFullInfo> findAllEmployeesFullInfo() {
        return employeeRepository.findAllEmployeeFullInfo();
    }

    @Override
    public List<EmployeeInfo> findAllEmployeesInfo() {
        return employeeRepository.findAllEmployeeInfo();
    }


    @Override
    public List<EmployeeDTO> allEmployeesToEmployeesDTO(List<Employee> employees) {
        return employees
                .stream()
                .map(EmployeeDTO::fromEmployee)
                .toList();
    }

    @Override
    public Integer getMaxSalary() {
        return employeeRepository.maxSalary();
    }

    @Override
    public EmployeeFullInfo getEmployeeFullInfoById(Integer id) {
        return EmployeeFullInfo.fromEmployee(employeeRepository.getEmployeeById(id));
    }

    @Override
    public List<EmployeeFullInfo> getEmployeeFullInfoWithMaxSalary() {
        return employeeRepository.getEmployeeFullInfoWithMaxSalary();
    }

    @Override
    public Position findByPositionId(Integer positionId) {
        return positionRepository.findByPositionId(positionId);
    }

    @Override
    public List<EmployeeFullInfo> getEmployeeFullInfoByPosition(Integer positionId) {
        List<EmployeeFullInfo> listAllEmployeesByPosition = new ArrayList<>();
        if (positionId == null) {
            listAllEmployeesByPosition = employeeRepository.findAllEmployeeFullInfo();
        } else {
            listAllEmployeesByPosition = positionRepository.findByPositionId(positionId)
                    .getEmployee()
                    .stream()
                    .map(EmployeeFullInfo::fromEmployee)
                    .toList();
        }
        return listAllEmployeesByPosition;
    }

    public List<EmployeeFullInfo> salaryHigherThan(Integer salary) {
        return employeeRepository.salaryHigherThan(salary);
    }

    public Employee generateRandomEmployees() {
        int randomSalary = (int) (Math.random() * 100000 + 50000);
        int randomPositionId = (int) Math.round(Math.random() * 24) + 1;
        int randomDivisionId = (int) Math.round(Math.random() * 4) + 1;
        Position randomPosition = positionRepository.findById(randomPositionId).orElseThrow();
        Division randomDivision = divisionRepository.findById(randomDivisionId).orElseThrow();
        Employee randomEmployee = new Employee();
        randomEmployee.setName(NameGenerator.randomName());
        randomEmployee.setSalary(randomSalary);
        randomEmployee.setPosition(randomPosition);
        randomEmployee.setDivision(randomDivision);
        return randomEmployee;
    }

    @Override
    public List<Employee> getEmployeeWithPaging(int pageIndex, int unitPerPage) {
        PageRequest employeeOfConcretePage = PageRequest.of(pageIndex, unitPerPage);
        Page<Employee> page = employeeRepository.findAll(employeeOfConcretePage);
        return page.stream()
                .toList();
    }

    @Override
    public List<EmployeeFullInfo> getEmployeeFullInfoWithPaging(int pageIndex, int unitPerPage) {
        PageRequest employeeOfConcretePage = PageRequest.of(pageIndex, unitPerPage);
        Page<Employee> page = employeeRepository.findAll(employeeOfConcretePage);
        return page
                .stream()
                .map(EmployeeFullInfo::fromEmployee)
                .toList();
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
    public EmployeeDTO getEmployeeById(Integer id) {
        return EmployeeDTO.fromEmployee(employeeRepository.getEmployeeById(id));
    }

    @Override
    public Employee editEmployeeById(Integer id, Employee employeeNew) {
        Employee employeeOld = employeeRepository.getEmployeeById(id);
        String nameNew = employeeNew.getName();
        Integer salaryNew = employeeNew.getSalary();
        Position positionNew = employeeNew.getPosition();

        if (!nameNew.isBlank())
        {employeeOld.setName(nameNew);}

        if (salaryNew !=0)
        {employeeOld.setSalary(salaryNew);}

        if (!(positionNew ==null))
        employeeOld.setPosition(positionNew);
        return employeeOld;
    }
}
