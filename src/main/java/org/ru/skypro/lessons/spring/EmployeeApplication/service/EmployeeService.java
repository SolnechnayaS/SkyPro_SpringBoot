package org.ru.skypro.lessons.spring.EmployeeApplication.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.ru.skypro.lessons.spring.EmployeeApplication.dto.EmployeeDTO;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Employee;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Position;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.projections.EmployeeFullInfo;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.projections.EmployeeInfo;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.projections.EmployeeView;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface EmployeeService {

    List<Employee> findAllEmployees();
    List<EmployeeView> findAllEmployeesView();
    List<EmployeeFullInfo> findAllEmployeesFullInfo();
    List<EmployeeInfo> findAllEmployeesInfo();

    List<EmployeeDTO> allEmployeesToEmployeesDTO(List<Employee> employees);

    Integer getMaxSalary();

    EmployeeFullInfo getEmployeeFullInfoById(Integer id);

    List<EmployeeFullInfo> getEmployeeFullInfoWithMaxSalary();
    Position findByPositionId (Long positionId);

    List<EmployeeFullInfo> getEmployeeFullInfoByPosition(Long positionId);

    List<EmployeeFullInfo> salaryHigherThan(Integer salary);

    Employee generateRandomEmployees();

    List<Employee> getEmployeeWithPaging(int pageIndex, int unitPerPage);
    List<EmployeeFullInfo> getEmployeeFullInfoWithPaging(int pageIndex, int unitPerPage);

    void deleteEmployeeById (int id);
    void addEmployee(Employee employee);

    EmployeeDTO getEmployeeById(Integer id);
    Employee editEmployeeById(Integer id, Employee employeeNew);

    String serializeEmployee(Employee employee) throws JsonProcessingException;

    Employee deserializeEmployee(String employee) throws JsonProcessingException;


    String serializeEmployeeDTO(EmployeeDTO employeeDTO) throws JsonProcessingException;

    EmployeeDTO deserializeEmployeeDTO(String employeeDTO) throws JsonProcessingException;

    List<EmployeeDTO> deserializeFileWithListEmployeeDTO(String fileName) throws IOException;

    List<EmployeeDTO> deserializeFileToEmployeeDTO(String fileName) throws IOException;

    void uploadEmployeesFromFile(MultipartFile multipartFile) throws IOException;

    Employee toEmployee(EmployeeDTO employeeDTO);

    List<Employee> allEmployeesDTOToEmployee(List<EmployeeDTO> employeesDTO);
}
