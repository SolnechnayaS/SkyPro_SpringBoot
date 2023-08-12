package org.ru.skypro.lessons.spring.EmployeeApplication.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.ru.skypro.lessons.spring.EmployeeApplication.dto.EmployeeDTO;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Employee;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Position;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.projections.EmployeeFullInfo;
import org.ru.skypro.lessons.spring.EmployeeApplication.repository.DivisionRepository;
import org.ru.skypro.lessons.spring.EmployeeApplication.repository.EmployeeRepository;
import org.ru.skypro.lessons.spring.EmployeeApplication.repository.PositionRepository;
import org.ru.skypro.lessons.spring.EmployeeApplication.repository.ReportRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.ru.skypro.lessons.spring.EmployeeApplication.constants.EmployeeServiceTestConstant.*;

@ExtendWith(MockitoExtension.class)
class ServiceImplTest {
    @Mock
    private EmployeeRepository employeeRepositoryMock;

    @Mock
    private PositionRepository positionRepositoryMock;

    @InjectMocks
    private EmployeeServiceImpl employeeServiceOut;

    @Mock
    private ReportRepository reportRepositoryMock;
    @InjectMocks
    private ReportServiceImpl reportServiceOut;

    @Mock
    private DivisionRepository divisionRepositoryMock;

    @Test
    void testFindAllEmployees() {
        when(employeeRepositoryMock.findAllEmployee())
                .thenReturn(LIST_ALL_EMPLOYEES);

        assertIterableEquals(LIST_ALL_EMPLOYEES, employeeServiceOut.findAllEmployees());

    }

    @Test
    void testFindAllEmployeesFullInfo() {
        when(employeeRepositoryMock.findAllEmployeeFullInfo())
                .thenReturn(LIST_ALL_EMPLOYEES_FULL_INFO);

        assertIterableEquals(LIST_ALL_EMPLOYEES_FULL_INFO, employeeServiceOut.findAllEmployeesFullInfo());

    }


    @ParameterizedTest
    @MethodSource("paramsForTestAllEmployeesToEmployeesDTO")
    public void testAllEmployeesToEmployeesDTO(List<Employee> employeeList, List<EmployeeDTO> employeeDTOList) {

        assertIterableEquals(employeeDTOList, employeeServiceOut.allEmployeesToEmployeesDTO(employeeList));

    }

    public static Stream<Arguments> paramsForTestAllEmployeesToEmployeesDTO() {
        return Stream.of(
                Arguments.of(LIST_EMPLOYEES_FOR_DIVISION_1, LIST_ALL_EMPLOYEES_DTO_FOR_DIVISION_1)
//                ,
//                Arguments.of(listEmployeesForDivision2, listAllEmployeesDTOForDivision2)
        );
    }

    @ParameterizedTest
    @MethodSource("paramsForTestEmployeeToEmployeeFullInfo")
    public void testEmployeeToEmployeeFullInfo(Employee employee, EmployeeFullInfo employeeFullInfo) {
        EmployeeFullInfo result = EmployeeFullInfo.fromEmployee(employee);
        assertEquals(employeeFullInfo, result);
    }

    public static Stream<Arguments> paramsForTestEmployeeToEmployeeFullInfo() {
        return Stream.of(
                Arguments.of(EMPLOYEE_1, EMPLOYEE_FULL_INFO_1),
                Arguments.of(EMPLOYEE_2, EMPLOYEE_FULL_INFO_2),
                Arguments.of(EMPLOYEE_3, EMPLOYEE_FULL_INFO_3),
                Arguments.of(EMPLOYEE_4, EMPLOYEE_FULL_INFO_4)
        );
    }

    @ParameterizedTest
    @MethodSource("paramsForGetEmployeeFullInfoById")
    void testGetEmployeeFullInfoById(Long id, Employee employee, EmployeeFullInfo employeeFullInfo) {
        when(employeeRepositoryMock.getEmployeeById(id))
                .thenReturn(employee);
        assertEquals(employeeFullInfo, employeeServiceOut.getEmployeeFullInfoById(id));
    }

    public static Stream<Arguments> paramsForGetEmployeeFullInfoById() {
        return Stream.of(
                Arguments.of(EMPLOYEE_ID_1, EMPLOYEE_1, EMPLOYEE_FULL_INFO_1),
                Arguments.of(EMPLOYEE_ID_2, EMPLOYEE_2, EMPLOYEE_FULL_INFO_2),
                Arguments.of(EMPLOYEE_ID_3, EMPLOYEE_3, EMPLOYEE_FULL_INFO_3),
                Arguments.of(EMPLOYEE_ID_4, EMPLOYEE_4, EMPLOYEE_FULL_INFO_4)
        );
    }

    @Test
    void testGetEmployeeFullInfoWithMaxSalary() {
        when(employeeRepositoryMock.getEmployeeFullInfoWithMaxSalary())
                .thenReturn(List.of(EMPLOYEE_FULL_INFO_4));

        assertIterableEquals(List.of(EMPLOYEE_FULL_INFO_4), employeeServiceOut.getEmployeeFullInfoWithMaxSalary());

    }

    @Test
    void testGetEmployeeFullInfoByPosition_PositionIdIsNull() {
        when(employeeRepositoryMock.findAllEmployeeFullInfo())
                .thenReturn(LIST_ALL_EMPLOYEES_FULL_INFO);
        assertIterableEquals(LIST_ALL_EMPLOYEES_FULL_INFO, employeeServiceOut.getEmployeeFullInfoByPosition(null));
    }

    @ParameterizedTest
    @MethodSource("paramsForGetEmployeeFullInfoByPosition")
    void testGetEmployeeFullInfoByPosition_PositionIdNotNull(Long positionId, Position position, List<Employee> employee, List<EmployeeFullInfo> employeeFullInfoList) {
        when(positionRepositoryMock.findByPositionId(positionId))
                .thenReturn(position);

        assertIterableEquals(employeeFullInfoList, employeeServiceOut.getEmployeeFullInfoByPosition(positionId));
    }

    public static Stream<Arguments> paramsForGetEmployeeFullInfoByPosition() {
        return Stream.of(
                Arguments.of(POSITION_ID_1, POSITION_1, LIST_EMPLOYEES_FOR_POSITION_1, LIST_ALL_EMPLOYEES_FULL_INFO_FOR_POSITION_1),
                Arguments.of(POSITION_ID_2, POSITION_2, LIST_EMPLOYEES_FOR_POSITION_2, LIST_ALL_EMPLOYEES_FULL_INFO_FOR_POSITION_2),
                Arguments.of(POSITION_ID_3, POSITION_3, LIST_EMPLOYEES_FOR_POSITION_3, LIST_ALL_EMPLOYEES_FULL_INFO_FOR_POSITION_3),
                Arguments.of(POSITION_ID_4, POSITION_4, LIST_EMPLOYEES_FOR_POSITION_4, LIST_ALL_EMPLOYEES_FULL_INFO_FOR_POSITION_4)
        );
    }

    @ParameterizedTest
    @MethodSource("paramsForTestSalaryHigherThan")
    void testSalaryHigherThan(Double salary, List<EmployeeFullInfo> employeeFullInfoList) {
        when(employeeRepositoryMock.salaryHigherThan(salary))
                .thenReturn(employeeFullInfoList);
        assertEquals(employeeFullInfoList, employeeServiceOut.salaryHigherThan(salary));
    }

    public static Stream<Arguments> paramsForTestSalaryHigherThan() {
        return Stream.of(
                Arguments.of(EMPLOYEE_SALARY_1, List.of(EMPLOYEE_FULL_INFO_1, EMPLOYEE_FULL_INFO_2, EMPLOYEE_FULL_INFO_3, EMPLOYEE_FULL_INFO_4)),
                Arguments.of(EMPLOYEE_SALARY_2, List.of(EMPLOYEE_FULL_INFO_2, EMPLOYEE_FULL_INFO_3, EMPLOYEE_FULL_INFO_4)),
                Arguments.of(EMPLOYEE_SALARY_3, List.of(EMPLOYEE_FULL_INFO_3, EMPLOYEE_FULL_INFO_4)),
                Arguments.of(EMPLOYEE_SALARY_4, List.of(EMPLOYEE_FULL_INFO_4))
        );
    }

    @ParameterizedTest
    @MethodSource("paramsEmployeeFullInfoWithPaging")
    void testGetEmployeeFullInfoWithPaging(int pageIndex, int unitPerPage, Page<Employee> page) {
        when(employeeRepositoryMock.findAll(PageRequest.of(pageIndex, unitPerPage)))
                .thenReturn(page);

        List<EmployeeFullInfo> expected = page
                .stream()
                .map(EmployeeFullInfo::fromEmployee)
                .toList();
        assertIterableEquals(expected, employeeServiceOut.getEmployeeFullInfoWithPaging(pageIndex, unitPerPage));
    }

    public static Stream<Arguments> paramsEmployeeFullInfoWithPaging() {
        return Stream.of(
                Arguments.of(PAGE_INDEX_0, UNIT_PER_PAGE_1, PAGE_1),
                Arguments.of(PAGE_INDEX_1, UNIT_PER_PAGE_2, PAGE_2)
        );
    }

    @ParameterizedTest
    @MethodSource("paramsAddRemoveEmployee")
    void testDeleteEmployeeById(Employee employee) {
        employeeServiceOut.deleteEmployeeById(employee.getId());
        verify(employeeRepositoryMock, times(1)).deleteById(employee.getId());

        //Вопрос к наставнику:
        // Добавила в сервис проверку на существование id
        // в этом модульном тесте на удаление сотрудника появилась ошибка:
        //org.ru.skypro.lessons.spring.EmployeeApplication.exception.UserNotFoundException
        //Как правильно скорректировать тест?
    }

    @ParameterizedTest
    @MethodSource("paramsAddRemoveEmployee")
    void testAddEmployee(Employee employee) {
        when(employeeRepositoryMock.save(employee)).thenReturn(employee);
        employeeServiceOut.addEmployee(employee);
        verify(employeeRepositoryMock, times(1)).save(employee);
    }


    public static Stream<Arguments> paramsAddRemoveEmployee() {
        return Stream.of(
                Arguments.of(EMPLOYEE_1),
                Arguments.of(EMPLOYEE_2),
                Arguments.of(EMPLOYEE_3),
                Arguments.of(EMPLOYEE_4)
        );
    }


    @ParameterizedTest
    @MethodSource("paramsEditEmployee")
    void testEditEmployeeById(Employee employee, Employee newEmployee, EmployeeFullInfo employeeFullInfo) {
        when(employeeRepositoryMock.getEmployeeById(employee.getId()))
                .thenReturn(employee);
        assertEquals(employeeFullInfo, EmployeeFullInfo.fromEmployee(employeeServiceOut.editEmployeeById(employee.getId(), newEmployee)));
    }

    public static Stream<Arguments> paramsEditEmployee() {
        return Stream.of(
//                Arguments.of(employee1, employee2, employeeFullInfo2),
//                Arguments.of(employee2, employee3, employeeFullInfo3),
                Arguments.of(EMPLOYEE_3, EMPLOYEE_4, EMPLOYEE_FULL_INFO_4)
//                ,
//                Arguments.of(employee4, employee1, employeeFullInfo2)
        );
    }

    @Test
    void testUploadEmployeesFromFile() throws IOException {
        File testFile = new File("src/test/java/org/ru/skypro/lessons/spring/EmployeeApplication/constants/fileToUpload.json");
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", new FileInputStream(testFile));

        assertIterableEquals(LIST_NEW_EMPLOYEES_DTO, employeeServiceOut.getEmployeesFromFile(mockMultipartFile));

    }

    @Test
    void testSaveReportStatisticsAllDivisions() throws IOException {
        assertEquals(SAVE_REPORT_STATISTICS_ALL_DIVISIONS, reportServiceOut.saveReportStatisticsAllDivisions(ALL_DIVISION_REPORT_STATISTICS));
    }

    @Test
    void testSaveReportStatisticsDivision() throws IOException {
        assertEquals(SAVE_REPORT_STATISTICS_MARKETING, reportServiceOut.saveReportStatisticsDivision(REPORT_STATISTICS_DIVISION_1));
    }

    @Test
    void testDownloadReportFile_NoSuchFile() throws IOException {
        when(reportRepositoryMock.findFilePathByReportId(1L))
                .thenReturn(String.valueOf(NON_EXISTENT_REPORT));

        assertThrows(NoSuchFileException.class, () -> reportServiceOut.downloadReportFile(1L));
    }

    @Test
    void testDownloadReportFile_OkFile() throws IOException {
        when(reportRepositoryMock.findFilePathByReportId(2L))
                .thenReturn(NEW_REPORT_MARKETING.getFilePath());
        assertEquals(SAVE_REPORT_STATISTICS_MARKETING, reportServiceOut.downloadReportFile(2L));

    }

}