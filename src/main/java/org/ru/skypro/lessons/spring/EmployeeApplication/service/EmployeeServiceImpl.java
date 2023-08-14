package org.ru.skypro.lessons.spring.EmployeeApplication.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ru.skypro.lessons.spring.EmployeeApplication.dto.EmployeeDTO;
import org.ru.skypro.lessons.spring.EmployeeApplication.exception.UserNotFoundException;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Division;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Employee;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Position;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.projections.EmployeeFullInfo;
import org.ru.skypro.lessons.spring.EmployeeApplication.repository.DivisionRepository;
import org.ru.skypro.lessons.spring.EmployeeApplication.repository.EmployeeRepository;
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

    public EmployeeDTO generateRandomEmployees() {

        Long randomPositionId = Math.round(Math.random() * 3) + 1;
        positionRepository.saveAll(List.of(new Position(1L, "Position1"),
                new Position(2L, "Position2"),
                new Position(3L, "Position3"),
                new Position(4L, "Position4")));

        Long randomDivisionId = Math.round(Math.random() * 1) + 1;
        divisionRepository.saveAll(List.of(new Division(1L, "Division1"),
                new Division(2L, "Division2")));

        Double randomSalary = randomPositionId * 100000.0;

        EmployeeDTO randomEmployee = new EmployeeDTO();
        randomEmployee.setName("Employee" + randomPositionId);
        randomEmployee.setSalary(randomSalary);
        randomEmployee.setPositionId(randomPositionId);
        randomEmployee.setDivisionId(randomDivisionId);
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
        if (employeeRepository.findById(id).orElse(null) == null) {
            throw new UserNotFoundException();
        } else {
            employeeRepository.deleteById(id);
            logger.info("delete Employee with id="+id);
        }
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

    @Override
    public void readEmployeeById(Long id, EmployeeDTO employeeNew) {
        Employee employeeOld = employeeRepository.findById(id).orElseThrow(UserNotFoundException::new);
        String nameNew = employeeNew.getName();
        Double salaryNew = employeeNew.getSalary();
        Long positionIdNew = employeeNew.getPositionId();
        Position positionNew = positionRepository.findByPositionId(positionIdNew);
        Long divisionIdNew = employeeNew.getDivisionId();
        Division divisionNew = divisionRepository.findByDivisionId(divisionIdNew);

        if (!nameNew.isBlank()) {
            employeeOld.setName(nameNew);
        }

        if (salaryNew != 0) {
            employeeOld.setSalary(salaryNew);
        }

        if (!(positionNew == null)) {
            employeeOld.setPosition(positionNew);
        }

        if (!(divisionNew == null)) {
            employeeOld.setDivision(divisionNew);
        }

        logger.info("edit Employee By Id");
        employeeRepository.save(employeeOld);
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
    public List<EmployeeDTO> getEmployeesFromFile(MultipartFile multipartFile) throws IOException {
        byte[] inputStream = multipartFile.getBytes();

        ObjectMapper objectMapper = new ObjectMapper();
        List<EmployeeDTO> uploadListEmployeeDTO = objectMapper.readValue(inputStream, new TypeReference<>() {
        });
        logger.info("upload Employees From File " + multipartFile.getOriginalFilename());
        return uploadListEmployeeDTO;
    }

    @Override
    public void uploadEmployeesFromListEmployeeDTO(List<EmployeeDTO> uploadListEmployeeDTO) throws IOException {
//        List<Employee> uploadListEmployee = allEmployeesDTOToEmployee(uploadListEmployeeDTO).stream()
//                .peek(this::addEmployee)
//                .toList();
        List<Employee> uploadListEmployee = allEmployeesDTOToEmployee(uploadListEmployeeDTO);
        employeeRepository.saveAll(uploadListEmployee);
        logger.info("В таблицу данных employees внесено " + uploadListEmployeeDTO.size() + " записей");
    }

    private Employee toEmployee(EmployeeDTO employeeDTO) {

        Employee employee = new Employee();

        employee.setId(employeeDTO.getId());
        employee.setName(employeeDTO.getName());
        employee.setSalary(employeeDTO.getSalary());

        employee.setPosition(positionRepository.findById(employeeDTO.getPositionId()).orElse(null));
        employee.setDivision(divisionRepository.findById(employeeDTO.getDivisionId()).orElse(null));

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
