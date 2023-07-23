package org.ru.skypro.lessons.spring.EmployeeApplication.constants;

import lombok.Data;
import org.ru.skypro.lessons.spring.EmployeeApplication.dto.EmployeeDTO;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Division;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Employee;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Position;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Report;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.projections.EmployeeFullInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@Data
public class EmployeeServiceTestConstant {
    public static final Long employeeId1 = 1L;
    public static final String employeeName1 = "Employee1";
    public static final Double employeeSalary1 = 100000.0;

    public static final Long employeeId2 = 2L;
    public static final String employeeName2 = "Employee2";
    public static final Double employeeSalary2 = 200000.0;

    public static final Long employeeId3 = 3L;
    public static final String employeeName3 = "Employee3";
    public static final Double employeeSalary3 = 300000.0;

    public static final Long employeeId4 = 4L;
    public static final String employeeName4 = "Employee4";
    public static final Double employeeSalary4 = 400000.0;

    public static final Long positionId1 = 1L;
    public static final String positionName1 = "Developer";
    public static final Position position1 = new Position(positionId1, positionName1);

    public static final Long positionId2 = 2L;
    public static final String positionName2 = "Administrator";
    public static final Position position2 = new Position(positionId2, positionName2);

    public static final Long positionId3 = 3L;
    public static final String positionName3 = "CEO";
    public static final Position position3 = new Position(positionId3, positionName3);

    public static final Long positionId4 = 4L;
    public static final String positionName4 = "CFO";
    public static final Position position4 = new Position(positionId4, positionName4);

    public static final List<Position> listAllPositions = List.of(position1, position2, position3, position4);

    public static final Long divisionId1 = 1L;
    public static final String divisionName1 = "IT";
    public static final Division division1 = new Division(divisionId1, divisionName1);

    public static final Long divisionId2 = 2L;
    public static final String divisionName2 = "Administration";
    public static final Division division2 = new Division(divisionId2, divisionName2);

    public static final List<Division> listAllDivisions = List.of(division1, division2);

    public static final Employee employee1 = new Employee(employeeId1, employeeName1, employeeSalary1, position1, division1);
    public static final Employee employee2 = new Employee(employeeId2, employeeName2, employeeSalary2, position2, division1);
    public static final Employee employee3 = new Employee(employeeId3, employeeName3, employeeSalary3, position3, division2);
    public static final Employee employee4 = new Employee(employeeId4, employeeName4, employeeSalary4, position4, division2);

    public static final List<Employee> listEmployeesForPosition1 = List.of(employee1);
    public static final List<Employee> listEmployeesForPosition2 = List.of(employee2);
    public static final List<Employee> listEmployeesForPosition3 = List.of(employee3);
    public static final List<Employee> listEmployeesForPosition4 = List.of(employee4);

    public static final List<Employee> listEmployeesForDivision1 = List.of(employee1, employee2);
    public static final List<Employee> listEmployeesForDivision2 = List.of(employee3, employee4);

    public static final List<Employee> listAllEmployees = List.of(employee1, employee2, employee3, employee4);

    public static final Long reportId = 1L;
    public static final String filePath = "src/main/java/org/ru/skypro/lessons/spring/EmployeeApplication/REPORTS/StatisticDivision_Administration_DT2023-07-05T22:07:59.785349.json";
    public static final Report report = new Report(reportId, filePath);

    public static final EmployeeFullInfo employeeFullInfo1 = new EmployeeFullInfo(employeeName1, employeeSalary1, positionName1);
    public static final EmployeeFullInfo employeeFullInfo2 = new EmployeeFullInfo(employeeName2, employeeSalary2, positionName2);
    public static final EmployeeFullInfo employeeFullInfo3 = new EmployeeFullInfo(employeeName3, employeeSalary3, positionName3);
    public static final EmployeeFullInfo employeeFullInfo4 = new EmployeeFullInfo(employeeName4, employeeSalary4, positionName4);

    public static final List<EmployeeFullInfo> listAllEmployeesFullInfo = List.of(employeeFullInfo1, employeeFullInfo2, employeeFullInfo3, employeeFullInfo4);
    public static final List<EmployeeFullInfo> listAllEmployeesFullInfoForPosition1 = List.of(employeeFullInfo1);
    public static final List<EmployeeFullInfo> listAllEmployeesFullInfoForPosition2 = List.of(employeeFullInfo2);
    public static final List<EmployeeFullInfo> listAllEmployeesFullInfoForPosition3 = List.of(employeeFullInfo3);
    public static final List<EmployeeFullInfo> listAllEmployeesFullInfoForPosition4 = List.of(employeeFullInfo4);
    public static final List<EmployeeFullInfo> listAllEmployeesFullInfoForDivision1 = List.of(employeeFullInfo1, employeeFullInfo2);
    public static final List<EmployeeFullInfo> listAllEmployeesFullInfoForDivision2 = List.of(employeeFullInfo3, employeeFullInfo4);

    public static final EmployeeDTO employeeDTO1 = new EmployeeDTO(employeeId1, employeeName1, employeeSalary1, divisionId1, positionId1);
    public static final EmployeeDTO employeeDTO2 = new EmployeeDTO(employeeId2, employeeName2, employeeSalary2, divisionId1, positionId2);
    public static final EmployeeDTO employeeDTO3 = new EmployeeDTO(employeeId3, employeeName3, employeeSalary3, divisionId2, positionId3);
    public static final EmployeeDTO employeeDTO4 = new EmployeeDTO(employeeId4, employeeName4, employeeSalary4, divisionId2, positionId4);

    public static final List<EmployeeDTO> listAllEmployeesDTO = List.of(employeeDTO1, employeeDTO2, employeeDTO3, employeeDTO4);
    public static final List<EmployeeDTO> listAllEmployeesDTOForPosition1 = List.of(employeeDTO1);
    public static final List<EmployeeDTO> listAllEmployeesDTOForPosition2 = List.of(employeeDTO2);
    public static final List<EmployeeDTO> listAllEmployeesDTOForPosition3 = List.of(employeeDTO3);
    public static final List<EmployeeDTO> listAllEmployeesDTOForPosition4 = List.of(employeeDTO4);
    public static final List<EmployeeDTO> listAllEmployeesDTOForDivision1 = List.of(employeeDTO1, employeeDTO2);
    public static final List<EmployeeDTO> listAllEmployeesDTOForDivision2 = List.of(employeeDTO3, employeeDTO4);


    public static final int pageIndex0 = 0;
    public static final int unitPerPage1 = 1;
//    public static final PageRequest employeeOfConcretePage1 = PageRequest.of(pageIndex0, unitPerPage1);

    public static final Page<Employee> page1 = new PageImpl<>(List.of(employee1));

    public static final int pageIndex1 = 1;
    public static final int unitPerPage2 = 2;
//    public static final PageRequest employeeOfConcretePage2 = PageRequest.of(pageIndex1, unitPerPage2);
    public static final Page<Employee> page2 = new PageImpl<>(List.of(employee3, employee4));
}
