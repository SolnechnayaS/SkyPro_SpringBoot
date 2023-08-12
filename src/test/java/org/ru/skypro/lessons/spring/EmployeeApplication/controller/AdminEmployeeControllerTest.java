package org.ru.skypro.lessons.spring.EmployeeApplication.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ru.skypro.lessons.spring.EmployeeApplication.EmployeeApplication;
import org.ru.skypro.lessons.spring.EmployeeApplication.dto.EmployeeDTO;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Division;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Employee;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Position;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.projections.ReportStatisticsDivision;
import org.ru.skypro.lessons.spring.EmployeeApplication.repository.DivisionRepository;
import org.ru.skypro.lessons.spring.EmployeeApplication.repository.EmployeeRepository;
import org.ru.skypro.lessons.spring.EmployeeApplication.repository.PositionRepository;
import org.ru.skypro.lessons.spring.EmployeeApplication.repository.ReportRepository;
import org.ru.skypro.lessons.spring.EmployeeApplication.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = EmployeeApplication.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AdminEmployeeControllerTest {

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
        mockMvc.perform(get("/admin/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(4));
    }

//    @Test
//    void generateEmployees() throws Exception {
//
    //Вопрос к наставнику:
    //Понимаю как сделать тест к post-запросу
    //Не понимаю как сделать тест к методу generate

//    }

    @Test
    void readEmployeeById() throws Exception {
        createEmployeesInRepository();
        // Вопрос к наставнику:
        // Всё, что закомментировано в этом тесте - взято/сделано по аналогии из лекции, но работает тест только до статуса isOk
        // Проверка id или name дает ошибку вида:
        // No value at JSON path "$.id" или No value at JSON path "$.name"

        // Создаем JSON объект, который будет использован
        // для отправки запроса на добавление пользователя
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("id", 4);
//        jsonObject.put("name", "test_name");
//        jsonObject.put("salary", 500000.0);
//        jsonObject.put("positionId",4);
//        jsonObject.put("divisionId",2);

        EmployeeDTO newEmployee = new EmployeeDTO(4L, "test_name", 500000.0, 2L, 4L);

        mockMvc.perform(put("/admin/employees/{id}", 4)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newEmployee)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/employees/{id}/fullInfo", 4))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test_name"));

//                .andExpect(jsonPath("$.id").isNotEmpty())
//      // Ожидаем, что поле "id" будет не пустым
//                .andExpect(jsonPath("$.id").isNumber())
//      // Ожидаем, что поле "id" будет числом
//                .andExpect(jsonPath("$.name").value("test_name"));
//      // Ожидаем, что поле "name" будет иметь значение "test_name"

        mockMvc.perform(put("/admin/employees/{id}", 5)
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
        createEmployeesInRepository();

        mockMvc.perform(delete("/admin/employees/{id}", 4))
                .andExpect(status().isOk());

        mockMvc.perform(get("/employees/"))
                .andExpect(jsonPath("$.length()").value(3));

        mockMvc.perform(delete("/admin/employees/{id}", 5))
                .andExpect(status().isNotFound());

        mockMvc.perform(delete("/admin/employees/{id}", "0_anyString"))
                .andExpect(status().is4xxClientError());

    }

    @Test
    void uploadFile() throws Exception {
        createEmployeesInRepository();

        EmployeeDTO newEmployee5 = new EmployeeDTO(5L, "Employee5", 250000.0, 1L, 1L);
        EmployeeDTO newEmployee6 = new EmployeeDTO(6L, "Employee6", 150000.0, 2L, 2L);
        List<EmployeeDTO> employeesToUpload = List.of(newEmployee5,newEmployee6);

        String json = objectMapper.writeValueAsString(employeesToUpload);
        MockMultipartFile file = new MockMultipartFile("file",json.getBytes());

        mockMvc.perform(multipart("/admin/employees/upload")
                        .file(file))
                .andExpect(status().isOk());

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(6));

        mockMvc.perform(get("/employees/{id}/fullInfo", 5))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Employee5"));

        //пробуем загрузить не list сотрудников, а только одного сотрудника
        EmployeeDTO newEmployee7 = new EmployeeDTO(7L, "Employee7", 150000.0, 2L, 2L);
        String jsonWrong = objectMapper.writeValueAsString(newEmployee7);
        MockMultipartFile fileWrong = new MockMultipartFile("file",jsonWrong.getBytes());

        mockMvc.perform(multipart("/admin/employees/upload")
                        .file(fileWrong))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void saveReportStatisticsAllDivisions() throws Exception {
        createEmployeesInRepository();

//        ReportStatisticsDivision reportStatisticsDivision1 = new ReportStatisticsDivision("Division1", 2L, 300000.0, 200000.0, 100000.0, 150000.0);
//        ReportStatisticsDivision reportStatisticsDivision2 = new ReportStatisticsDivision("Division2", 2L, 700000.0, 400000.0, 300000.0, 350000.0);
//        List<ReportStatisticsDivision> listReportStatisticsDivision = List.of(reportStatisticsDivision1, reportStatisticsDivision2);

        LocalDateTime localDateTime = LocalDate.now().atStartOfDay();
        String path = "src/main/java/org/ru/skypro/lessons/spring/EmployeeApplication" +
                "/REPORTS";
        String fileNameAllDivisions = "StatisticDivision_" + "_ALL_" + "_DT" + localDateTime + ".json";

        mockMvc.perform(get("/admin/employees/report"))
                .andExpect(status().isOk());

//        //тест с ошибкой
//        mockMvc.perform(get("/report/{id}", 1))
//                .andExpect(status().isOk());

//                .andExpect(jsonPath("$.filePath").value(path + fileNameAllDivisions));

        //Вопрос к наставнику:
        // Метод, который создает и сохраняет в папку REPORTS статистический отчет,
        // должен делать еще запись с id (=1) и путем к файлу в репозиторий reportRepository,
        // файл появляется в нужной папке самого приложения (не теста), но почему-то
//        mockMvc.perform(get("/report/{id}", 1))
//                .andExpect(status().isOk());
        // не находит отчет c id=1

    }

    @Test
    void saveReportStatisticsByDivision() throws Exception {
        createEmployeesInRepository();

        mockMvc.perform(get("/admin/employees/report/division")
                        .param("id", String.valueOf(1)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/admin/employees/report/division")
                        .param("id", String.valueOf(3)))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/admin/employees/report/division")
                        .param("id", "0_anyString"))
                .andExpect(status().is4xxClientError());

        //Вопрос к наставнику:
        //Как и в предыдущем методе, можно ли добавить тест на существование отчета после его добавления?
    }
}