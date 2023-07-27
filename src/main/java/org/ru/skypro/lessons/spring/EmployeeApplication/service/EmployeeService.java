package org.ru.skypro.lessons.spring.EmployeeApplication.service;

import org.ru.skypro.lessons.spring.EmployeeApplication.dto.EmployeeDTO;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Employee;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.projections.EmployeeFullInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface EmployeeService {

    List<Employee> findAllEmployees();

    List<EmployeeFullInfo> findAllEmployeesFullInfo();

    EmployeeFullInfo getEmployeeFullInfoById(Long id);

    List<EmployeeFullInfo> getEmployeeFullInfoWithMaxSalary();

    List<EmployeeFullInfo> getEmployeeFullInfoByPosition(Long positionId);

    List<EmployeeDTO> allEmployeesToEmployeesDTO(List<Employee> employees);

    List<EmployeeFullInfo> salaryHigherThan(Double salary);

    Employee generateRandomEmployees();

    List<EmployeeFullInfo> getEmployeeFullInfoWithPaging(int pageIndex, int unitPerPage);

    void deleteEmployeeById(Long id);

    void addEmployee(Employee employee);

    Employee editEmployeeById(Long id, Employee employeeNew);

    //    void uploadEmployeesFromFile(MultipartFile multipartFile) throws IOException;
    List<EmployeeDTO> uploadEmployeesFromFile(MultipartFile multipartFile) throws IOException;

}
