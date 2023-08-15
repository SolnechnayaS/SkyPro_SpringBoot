package org.ru.skypro.lessons.spring.EmployeeApplication.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ru.skypro.lessons.spring.EmployeeApplication.EmployeeApplication;
import org.ru.skypro.lessons.spring.EmployeeApplication.dto.EmployeeDTO;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Division;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Employee;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Position;
import org.ru.skypro.lessons.spring.EmployeeApplication.repository.DivisionRepository;
import org.ru.skypro.lessons.spring.EmployeeApplication.repository.EmployeeRepository;
import org.ru.skypro.lessons.spring.EmployeeApplication.repository.PositionRepository;
import org.ru.skypro.lessons.spring.EmployeeApplication.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.ru.skypro.lessons.spring.EmployeeApplication.controller.EmployeeControllerTestConstant.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = EmployeeApplication.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

@Testcontainers
class AdminEmployeeControllerTest {

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest")
            .withUsername("postgres")
            .withPassword("postgres");

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private DataSource dataSource;

    @Test
    void testPostgresql() throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            assertThat(conn).isNotNull();
        }
    }

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

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void fillRepository() {
        positionRepository.saveAll(List.of(POSITION_1, POSITION_2, POSITION_3, POSITION_4));
        divisionRepository.saveAll(List.of(DIVISION_1, DIVISION_2));
        List<Employee> employeeList = allEmployeesDTOToEmployee(List.of(EMPLOYEE_DTO_1, EMPLOYEE_DTO_2, EMPLOYEE_DTO_3, EMPLOYEE_DTO_4));
        employeeRepository.saveAll(employeeList);
    }


    private Employee toEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setId(employeeDTO.getId());
        employee.setName(employeeDTO.getName());
        employee.setSalary(employeeDTO.getSalary());
        employee.setPosition(positionRepository.findById(employeeDTO.getPositionId()).orElse(null));
        employee.setDivision(divisionRepository.findById(employeeDTO.getDivisionId()).orElse(null));
        return employee;
    }

    private List<Employee> allEmployeesDTOToEmployee(List<EmployeeDTO> employeesDTO) {
        return employeesDTO
                .stream()
                .map(this::toEmployee)
                .toList();
    }

    private Long findLastEmployeeID() {
        return employeeRepository.findLastEmployeeId();
    }

    private Long countEmployeeID() {
        return employeeRepository.count();
    }

    @Test
    void testFindAllEmployees() throws Exception {
        Long count = countEmployeeID();
        mockMvc.perform(get("/admin/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(count));

    }

    @Test
    void generateEmployees() throws Exception {
        Integer number = 1;
        mockMvc.perform(post("/admin/employees/generate").param("number", String.valueOf(number))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Long count = countEmployeeID();
        mockMvc.perform(get("/admin/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(count));
    }

    @Test
    void readEmployeeById() throws Exception {
        Long lastEmployeeId = findLastEmployeeID();
        EmployeeDTO newEmployee = new EmployeeDTO(lastEmployeeId, "test_name", 500000.0, 2L, 4L);

        Long count = countEmployeeID();
        mockMvc.perform(put("/admin/employees/{id}", lastEmployeeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newEmployee)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/employees/{id}/fullInfo", lastEmployeeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test_name"));

        mockMvc.perform(put("/admin/employees/{id}", count+100)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newEmployee)))
                .andExpect(status().isNotFound());

        mockMvc.perform(put("/admin/employees/{id}", "0_anyString")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newEmployee)))
                .andExpect(status().is4xxClientError());

    }

    @Test
    void testDeleteEmployeeById() throws Exception {
        Long lastEmployeeId = findLastEmployeeID();
        Long count = countEmployeeID();

        mockMvc.perform(delete("/admin/employees/{id}", lastEmployeeId))
                .andExpect(status().isOk());

        mockMvc.perform(get("/employees/"))
                .andExpect(jsonPath("$.length()").value(count-1));

        mockMvc.perform(delete("/admin/employees/{id}", count+1))
                .andExpect(status().isNotFound());

        mockMvc.perform(delete("/admin/employees/{id}", "0_anyString"))
                .andExpect(status().is4xxClientError());

    }

    @Test
    void uploadFile() throws Exception {
        Long lastEmployeeId = findLastEmployeeID();

        EmployeeDTO newEmployee5 = new EmployeeDTO(lastEmployeeId+1, "Employee5", 250000.0, 1L, 1L);
        EmployeeDTO newEmployee6 = new EmployeeDTO(lastEmployeeId+2, "Employee6", 150000.0, 2L, 2L);
        List<EmployeeDTO> employeesToUpload = List.of(newEmployee5, newEmployee6);

        String json = objectMapper.writeValueAsString(employeesToUpload);
        MockMultipartFile file = new MockMultipartFile("file", json.getBytes());

        mockMvc.perform(multipart("/admin/employees/upload")
                        .file(file))
                .andExpect(status().isOk());

        Long count = countEmployeeID();

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(count));

        mockMvc.perform(get("/employees/{id}/fullInfo", lastEmployeeId+1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Employee5"));

        //пробуем загрузить не list сотрудников, а только одного сотрудника
        EmployeeDTO newEmployee7 = new EmployeeDTO(lastEmployeeId+3, "Employee7", 150000.0, 2L, 2L);
        String jsonWrong = objectMapper.writeValueAsString(newEmployee7);
        MockMultipartFile fileWrong = new MockMultipartFile("file", jsonWrong.getBytes());

        mockMvc.perform(multipart("/admin/employees/upload")
                        .file(fileWrong))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void saveReportStatisticsAllDivisions() throws Exception {
        mockMvc.perform(get("/admin/employees/report"))
                .andExpect(status().isOk());
    }

    @Test
    void saveReportStatisticsByDivision() throws Exception {
        Long count = divisionRepository.count();

        mockMvc.perform(get("/admin/employees/report/division")
                        .param("id", String.valueOf(1)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/admin/employees/report/division")
                        .param("id", String.valueOf(count+1)))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/admin/employees/report/division")
                        .param("id", "0_anyString"))
                .andExpect(status().is4xxClientError());

    }
}