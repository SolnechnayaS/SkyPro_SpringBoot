package org.ru.skypro.lessons.spring.EmployeeApplication.service;

import org.ru.skypro.lessons.spring.EmployeeApplication.dto.EmployeeDTO;
import org.ru.skypro.lessons.spring.EmployeeApplication.exception.IncorrectEmployeeIdException;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Employee;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Position;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.projections.EmployeeFullInfo;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.projections.EmployeeInfo;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.projections.EmployeeView;

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
    Position findByPositionId (Integer positionId);

    List<EmployeeFullInfo> getEmployeeFullInfoByPosition(Integer positionId);

    List<EmployeeFullInfo> salaryHigherThan(Integer salary);

    Employee generateRandomEmployees();

    List<Employee> getEmployeeWithPaging(int pageIndex, int unitPerPage);
    List<EmployeeFullInfo> getEmployeeFullInfoWithPaging(int pageIndex, int unitPerPage);

    void deleteEmployeeById (int id);
    void addEmployee(Employee employee);

    EmployeeDTO getEmployeeById(Integer id);
    Employee editEmployeeById(Integer id, Employee employeeNew);
}
