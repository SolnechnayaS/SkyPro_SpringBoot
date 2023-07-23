package org.ru.skypro.lessons.spring.EmployeeApplication.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
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
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.ru.skypro.lessons.spring.EmployeeApplication.constants.EmployeeServiceTestConstant.*;
import static org.ru.skypro.lessons.spring.EmployeeApplication.constants.EmployeeServiceTestConstant.employeeFullInfo4;

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
                .thenReturn(listAllEmployees);

        assertIterableEquals(listAllEmployees, employeeServiceOut.findAllEmployees());

    }

    @Test
    void testFindAllEmployeesFullInfo() {
        when(employeeRepositoryMock.findAllEmployeeFullInfo())
                .thenReturn(listAllEmployeesFullInfo);

        assertIterableEquals(listAllEmployeesFullInfo, employeeServiceOut.findAllEmployeesFullInfo());

    }


    @ParameterizedTest
    @MethodSource("paramsForTestAllEmployeesToEmployeesDTO")
    public void testAllEmployeesToEmployeesDTO(List <Employee> employeeList, List<EmployeeDTO> employeeDTOList) {

        assertIterableEquals(employeeDTOList, employeeServiceOut.allEmployeesToEmployeesDTO(employeeList));

        // По поводу данного метода требуется поснение:
        // если запускаю этот тест отдельно - все хорошо
        // если запускаю все тесты класса разом - в этом методе (единственном) возникает несоответствие между ожидание/реальность
        // приводит к несоответствию метод на 280 строке: testEditEmployeeById, который редактирует данные сотрудников
        // если закомментировать метод testEditEmployeeById - то testAllEmployeesToEmployeesDTO отрабатывает отлично
        // Почему так?
    }

    public static Stream<Arguments> paramsForTestAllEmployeesToEmployeesDTO() {
        return Stream.of(
                Arguments.of(listEmployeesForDivision1, listAllEmployeesDTOForDivision1),
                Arguments.of(listEmployeesForDivision2, listAllEmployeesDTOForDivision2)
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
                Arguments.of(employee1, employeeFullInfo1),
                Arguments.of(employee2, employeeFullInfo2),
                Arguments.of(employee3, employeeFullInfo3),
                Arguments.of(employee4, employeeFullInfo4)
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
                Arguments.of(employeeId1, employee1, employeeFullInfo1),
                Arguments.of(employeeId2, employee2, employeeFullInfo2),
                Arguments.of(employeeId3, employee3, employeeFullInfo3),
                Arguments.of(employeeId4, employee4, employeeFullInfo4)
        );
    }

    @Test
    void testGetEmployeeFullInfoWithMaxSalary() {
        when(employeeRepositoryMock.getEmployeeFullInfoWithMaxSalary())
                .thenReturn(List.of(employeeFullInfo4));

        assertIterableEquals(List.of(employeeFullInfo4), employeeServiceOut.getEmployeeFullInfoWithMaxSalary());

    }

    @Test
    void testGetEmployeeFullInfoByPosition_PositionIdIsNull() {
        when(employeeRepositoryMock.findAllEmployeeFullInfo())
                .thenReturn(listAllEmployeesFullInfo);
        assertIterableEquals(listAllEmployeesFullInfo, employeeServiceOut.getEmployeeFullInfoByPosition(null));
    }

    @ParameterizedTest
    @MethodSource("paramsForGetEmployeeFullInfoByPosition")
    void testGetEmployeeFullInfoByPosition_PositionIdNotNull(Long positionId, Position position, List<Employee> employee, List<EmployeeFullInfo> employeeFullInfoList) {
        when(positionRepositoryMock.findByPositionId(positionId))
                .thenReturn(position);

        position.setEmployee(employee);
        //Вопрос к наставнику - корректно ли так делать? В классе с тестовыми константами не могу использовать геттеры и сеттеры (Почему?)

        assertIterableEquals(employeeFullInfoList, employeeServiceOut.getEmployeeFullInfoByPosition(positionId));
    }

    public static Stream<Arguments> paramsForGetEmployeeFullInfoByPosition() {
        return Stream.of(
                Arguments.of(positionId1, position1, listEmployeesForPosition1, listAllEmployeesFullInfoForPosition1),
                Arguments.of(positionId2, position2, listEmployeesForPosition2, listAllEmployeesFullInfoForPosition2),
                Arguments.of(positionId3, position3, listEmployeesForPosition3, listAllEmployeesFullInfoForPosition3),
                Arguments.of(positionId4, position4, listEmployeesForPosition4, listAllEmployeesFullInfoForPosition4)
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
                Arguments.of(employeeSalary1, List.of(employeeFullInfo1, employeeFullInfo2, employeeFullInfo3, employeeFullInfo4)),
                Arguments.of(employeeSalary2, List.of(employeeFullInfo2, employeeFullInfo3, employeeFullInfo4)),
                Arguments.of(employeeSalary3, List.of(employeeFullInfo3, employeeFullInfo4)),
                Arguments.of(employeeSalary4, List.of(employeeFullInfo4))
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
                Arguments.of(pageIndex0, unitPerPage1, page1),
                Arguments.of(pageIndex1, unitPerPage2, page2)
        );
    }

    @ParameterizedTest
    @MethodSource("paramsAddRemoveEmployee")
    void testDeleteEmployeeById(Employee employee) {

    //не понимаю как сделать тест для данного метода

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
                Arguments.of(employee1),
                Arguments.of(employee2),
                Arguments.of(employee3),
                Arguments.of(employee4)
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
                Arguments.of(employee1, employee2, employeeFullInfo2),
                Arguments.of(employee2, employee3, employeeFullInfo3),
                Arguments.of(employee3, employee4, employeeFullInfo4),
                Arguments.of(employee4, employee1, employeeFullInfo2)
        );
    }

    @Test
    void testUploadEmployeesFromFile() throws JsonProcessingException {

//Здесь тоже нужна помощь

    }

}