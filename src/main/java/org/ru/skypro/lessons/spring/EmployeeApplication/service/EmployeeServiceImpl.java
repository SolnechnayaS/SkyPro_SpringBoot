package org.ru.skypro.lessons.spring.EmployeeApplication.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ru.skypro.lessons.spring.EmployeeApplication.dto.EmployeeDTO;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Division;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Employee;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Position;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.projections.EmployeeFullInfo;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.projections.EmployeeInfo;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.projections.EmployeeView;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.projections.ReportStatisticsDivision;
import org.ru.skypro.lessons.spring.EmployeeApplication.repository.DivisionRepository;
import org.ru.skypro.lessons.spring.EmployeeApplication.repository.EmployeeRepository;
import org.ru.skypro.lessons.spring.EmployeeApplication.repository.NameGenerator;
import org.ru.skypro.lessons.spring.EmployeeApplication.repository.PositionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
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
        logger.info("find All Employees");
        return employeeRepository.findAllEmployee();
    }

    @Override
    public List<EmployeeFullInfo> findAllEmployeesFullInfo() {
        logger.info("find All Employees (EmployeeFullInfo)");
        return employeeRepository.findAllEmployeeFullInfo();
    }

    @Override
    public List<EmployeeDTO> allEmployeesToEmployeesDTO(List<Employee> employees) {
        logger.info("Converting All Employees to EmployeesDTO");
        return employees
                .stream()
                .map(EmployeeDTO::fromEmployee)
                .toList();
    }

    @Override
    public EmployeeFullInfo getEmployeeFullInfoById(Long id) {
        logger.info("get Employee (EmployeeFullInfo) By Id");
        return EmployeeFullInfo.fromEmployee(employeeRepository.getEmployeeById(id));
    }

    @Override
    public List<EmployeeFullInfo> getEmployeeFullInfoWithMaxSalary() {
        logger.info("get Employee (EmployeeFullInfo) With Max Salary");
        return employeeRepository.getEmployeeFullInfoWithMaxSalary();
    }

    @Override
    public List<EmployeeFullInfo> getEmployeeFullInfoByPosition(Long positionId) {
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
        logger.info("find Employees (EmployeeFullInfo) By Position's Id");
        return listAllEmployeesByPosition;
    }

    public List<EmployeeFullInfo> salaryHigherThan(Double salary) {
        logger.info("search for employees with a salary above the level=" + salary);
        return employeeRepository.salaryHigherThan(salary);
    }

    public Employee generateRandomEmployees() {
        Double randomSalary = Math.random() * 100000 + 50000;
        Integer randomPositionId = (int) (Math.round(Math.random() * 24) + 1);
        Integer randomDivisionId = (int) (Math.round(Math.random() * 4) + 1);
        Position randomPosition = positionRepository.findById(randomPositionId).orElseThrow();
        Division randomDivision = divisionRepository.findById(randomDivisionId).orElseThrow();
        Employee randomEmployee = new Employee();
        randomEmployee.setName(NameGenerator.randomName());
        randomEmployee.setSalary(randomSalary);
        randomEmployee.setPosition(randomPosition);
        randomEmployee.setDivision(randomDivision);
        logger.info("generating random employees");
        return randomEmployee;
    }

    @Override
    public List<EmployeeFullInfo> getEmployeeFullInfoWithPaging(int pageIndex, int unitPerPage) {
        PageRequest employeeOfConcretePage = PageRequest.of(pageIndex, unitPerPage);
        Page<Employee> page = employeeRepository.findAll(employeeOfConcretePage);
        logger.info("get Employee (EmployeeFullInfo) With Paging");
        return page
                .stream()
                .map(EmployeeFullInfo::fromEmployee)
                .toList();
    }

    @Override
    public void deleteEmployeeById(Long id) {
        logger.info("delete Employee By Id");
        employeeRepository.deleteById(id);
    }

    @Override
    public void addEmployee(Employee employee) {
        logger.info("add new Employee");
        employeeRepository.save(employee);
    }

    @Override
    public Employee editEmployeeById(Long id, Employee employeeNew) {
        Employee employeeOld = employeeRepository.getEmployeeById(id);
        String nameNew = employeeNew.getName();
        Double salaryNew = employeeNew.getSalary();
        Position positionNew = employeeNew.getPosition();

        if (!nameNew.isBlank()) {
            employeeOld.setName(nameNew);
        }

        if (salaryNew != 0) {
            employeeOld.setSalary(salaryNew);
        }

        if (!(positionNew == null)) {
            employeeOld.setPosition(positionNew);
        }

        logger.info("edit Employee By Id");
        return employeeOld;
    }

    private String serializeEmployeeDTO(EmployeeDTO employeeDTO) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        logger.info("serialize EmployeeDTO");
        return objectMapper.writeValueAsString(employeeDTO);
    }

    private EmployeeDTO deserializeEmployeeDTO(String employeeDTO) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        logger.info("deserialize EmployeeDTO");
        return objectMapper.readValue(employeeDTO, EmployeeDTO.class);
    }

    private List<EmployeeDTO> deserializeFileWithListEmployeeDTO(String fileName) throws IOException {
        File file = new File(fileName);
        ObjectMapper objectMapper = new ObjectMapper();
        logger.info("deserialize File With List EmployeeDTO");
        return objectMapper.readValue(file, new TypeReference<>() {
        });
    }

    private List<EmployeeDTO> deserializeFileToEmployeeDTO(String fileName) throws IOException {
        File file = new File(fileName);
        ObjectMapper objectMapper = new ObjectMapper();

        logger.info("deserialize File With List EmployeeDTO");
        return objectMapper.readValue(file, new TypeReference<>() {
        });
    }

    @Override
    public void uploadEmployeesFromFile(MultipartFile multipartFile) throws IOException {
        byte[] inputStream = multipartFile.getBytes();

        ObjectMapper objectMapper = new ObjectMapper();
        List<EmployeeDTO> uploadListEmployeeDTO = objectMapper.readValue(inputStream, new TypeReference<>() {
        });
        allEmployeesDTOToEmployee(uploadListEmployeeDTO)
                .forEach(this::addEmployee);

        logger.info("upload Employees From File " + multipartFile.getOriginalFilename());
        logger.info("В таблицу данных employees внесено " + uploadListEmployeeDTO.size() + " записей");

    }

    private Employee toEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setId(employeeDTO.getId());
        employee.setName(employeeDTO.getName());
        employee.setSalary(employeeDTO.getSalary());
        employee.setPosition(positionRepository.findByPositionId(employeeDTO.getPositionId()));
        employee.setDivision(divisionRepository.findByDivisionId(employeeDTO.getDivisionId()));
        logger.info("Converting EmployeeDTO to Employee");
        return employee;
    }

    private List<Employee> allEmployeesDTOToEmployee(List<EmployeeDTO> employeesDTO) {
        logger.info("Converting List EmployeeDTO to List Employee");
        return employeesDTO
                .stream()
                .map(this::toEmployee)
                .toList();
    }
}
