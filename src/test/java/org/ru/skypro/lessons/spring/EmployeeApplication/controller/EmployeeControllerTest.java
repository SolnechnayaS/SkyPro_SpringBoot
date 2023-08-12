package org.ru.skypro.lessons.spring.EmployeeApplication.controller;

import org.junit.jupiter.api.Test;
import org.ru.skypro.lessons.spring.EmployeeApplication.EmployeeApplication;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Division;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Employee;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Position;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Report;
import org.ru.skypro.lessons.spring.EmployeeApplication.repository.DivisionRepository;
import org.ru.skypro.lessons.spring.EmployeeApplication.repository.EmployeeRepository;
import org.ru.skypro.lessons.spring.EmployeeApplication.repository.PositionRepository;
import org.ru.skypro.lessons.spring.EmployeeApplication.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = EmployeeApplication.class)
@AutoConfigureMockMvc
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DivisionRepository divisionRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private ReportRepository reportRepository;

    void createEmployeesInRepository() {
        Employee employee1 = new Employee(1L, "Employee1", 100000.0);
        Employee employee2 = new Employee(2L, "Employee2", 200000.0);
        Employee employee3 = new Employee(3L, "Employee3", 300000.0);
        Employee employee4 = new Employee(4L, "Employee4", 400000.0);
        List<Employee> employeeList = List.of(employee1, employee2, employee3, employee4);
        employeeRepository.saveAll(employeeList);

        List<Employee> listEmployeesForPosition1 = List.of(employee1);
        List<Employee> listEmployeesForPosition2 = List.of(employee2);
        List<Employee> listEmployeesForPosition3 = List.of(employee3);
        List<Employee> listEmployeesForPosition4 = List.of(employee4);

        Position position1 = new Position(1L, "Position1", listEmployeesForPosition1);
        Position position2 = new Position(2L, "Position2", listEmployeesForPosition2);
        Position position3 = new Position(3L, "Position3", listEmployeesForPosition3);
        Position position4 = new Position(4L, "Position4", listEmployeesForPosition4);
        List<Position> positionList = List.of(position1, position2, position3, position4);
        positionRepository.saveAll(positionList);

        List<Employee> listEmployeesForDivision1 = List.of(employee1, employee2);
        List<Employee> listEmployeesForDivision2 = List.of(employee3, employee4);

        Division division1 = new Division(1L, "Division1", listEmployeesForDivision1);
        Division division2 = new Division(2L, "Division2", listEmployeesForDivision2);
        List<Division> divisionList = List.of(division1, division2);
        divisionRepository.saveAll(divisionList);

        employee1.setPosition(position1);
        employee1.setDivision(division1);
        employeeRepository.save(employee1);

        employee2.setPosition(position2);
        employee2.setDivision(division1);
        employeeRepository.save(employee2);

        employee3.setPosition(position3);
        employee3.setDivision(division2);
        employeeRepository.save(employee3);

        employee4.setPosition(position4);
        employee4.setDivision(division2);
        employeeRepository.save(employee4);

    }

    @Test
    void testFindAllEmployees() throws Exception {
        createEmployeesInRepository();
        mockMvc.perform(get("/employees/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(4));
    }

    @Test
    void testSalaryHigherThan() throws Exception {
        createEmployeesInRepository();
        Double requestSalary = 200000.0;
        int expectedCount = 3;

        mockMvc.perform(get("/employees/salary/HigherThan")
                        .param("salary", String.valueOf(requestSalary)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(expectedCount));

        mockMvc.perform(get("/employees/salary/HigherThan")
                        .param("salary", "0_anyString"))
                .andExpect(status().is4xxClientError());
    }
    @Test
    void testGetEmployeeFullInfoWithMaxSalary() throws Exception {
        createEmployeesInRepository();
        int expectedCount = 1;

        mockMvc.perform(get("/employees/withHighestSalary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(expectedCount))
                .andExpect(jsonPath("[0].name").value("Employee4"));

    }

    @Test
    void getAllEmployeesByPosition() throws Exception {
        createEmployeesInRepository();

        mockMvc.perform(get("/employees").param("position", String.valueOf(1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("[0].name").value("Employee1"));

        mockMvc.perform(get("/employees").param("position", ""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(4));

        mockMvc.perform(get("/employees").param("position", String.valueOf(5)))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/employees").param("position", "0_anyString"))
                .andExpect(status().is4xxClientError());

    }

    @Test
    void readEmployeeByIdFullInfo() throws Exception {
        createEmployeesInRepository();

        mockMvc.perform(get("/employees/{id}/fullInfo", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Employee1"));

        mockMvc.perform(get("/employees/{id}/fullInfo", 111))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/employees/{id}/fullInfo", "0_anyString"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void getEmployeeWithPaging() throws Exception {
        createEmployeesInRepository();

        mockMvc.perform(get("/employees/page")
                        .param("page", String.valueOf(1))
                        .param("size", String.valueOf(10)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));

        mockMvc.perform(get("/employees/page")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(3)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3));

        mockMvc.perform(get("/employees/page")
                        .param("page", String.valueOf(1))
                        .param("size", String.valueOf(3)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));

        mockMvc.perform(get("/employees/page")
                        .param("page", "")
                        .param("size", ""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));

        mockMvc.perform(get("/employees/page")
                        .param("page", "")
                        .param("size", String.valueOf(3)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));

        mockMvc.perform(get("/employees/page")
                        .param("page", String.valueOf(0))
                        .param("size", ""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(4));

        mockMvc.perform(get("/employees/page")
                        .param("page", "0_anyString")
                        .param("size", "0_anyString"))
                .andExpect(status().is4xxClientError());

    }

    @Test
    void testFindAllEmployeesFullInfo() throws Exception {
        createEmployeesInRepository();

        mockMvc.perform(get("/employees/fullInfo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(4));
    }

    @Test
    void downloadReportFile() throws Exception {
        createEmployeesInRepository();
        reportRepository.save(new Report(1L,"src/test/java/org/ru/skypro/lessons/spring/EmployeeApplication/constants/REPORTS/StatisticDivision__ALL__DT2023-08-13T00:00.json"));
        reportRepository.save(new Report(2L,"src/test/java/org/ru/skypro/lessons/spring/EmployeeApplication/constants/REPORTS/StatisticDivision_Marketing_DT2023-08-13T00:00.json"));

        mockMvc.perform(get("/employees/report/{id}",1))
                .andExpect(status().isOk());

        mockMvc.perform(get("/employees/report/{id}",3))
                .andExpect(status().isNotFound());

        mockMvc.perform(get("/employees/report/{id}","0_anyString"))
                .andExpect(status().is4xxClientError());
    }
}