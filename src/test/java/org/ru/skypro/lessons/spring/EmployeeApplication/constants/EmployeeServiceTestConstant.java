package org.ru.skypro.lessons.spring.EmployeeApplication.constants;

import lombok.Data;
import org.ru.skypro.lessons.spring.EmployeeApplication.dto.EmployeeDTO;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Division;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Employee;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Position;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Report;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.projections.EmployeeFullInfo;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.projections.ReportStatisticsDivision;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static java.nio.file.Files.writeString;

@Data
public class EmployeeServiceTestConstant {
    public static final Long EMPLOYEE_ID_1 = 1L;
    public static final String EMPLOYEE_NAME_1 = "Employee1";
    public static final Double EMPLOYEE_SALARY_1 = 100000.0;

    public static final Long EMPLOYEE_ID_2 = 2L;
    public static final String EMPLOYEE_NAME_2 = "Employee2";
    public static final Double EMPLOYEE_SALARY_2 = 200000.0;

    public static final Long EMPLOYEE_ID_3 = 3L;
    public static final String EMPLOYEE_NAME_3 = "Employee3";
    public static final Double EMPLOYEE_SALARY_3 = 300000.0;

    public static final Long EMPLOYEE_ID_4 = 4L;
    public static final String EMPLOYEE_NAME_4 = "Employee4";
    public static final Double EMPLOYEE_SALARY_4 = 400000.0;

    public static final Long POSITION_ID_1 = 1L;
    public static final String POSITION_NAME_1 = "Developer";
    public static final Position POSITION_1 = new Position(POSITION_ID_1, POSITION_NAME_1);

    public static final Long POSITION_ID_2 = 2L;
    public static final String POSITION_NAME_2 = "Administrator";
    public static final Position POSITION_2 = new Position(POSITION_ID_2, POSITION_NAME_2);

    public static final Long POSITION_ID_3 = 3L;
    public static final String POSITION_NAME_3 = "CEO";
    public static final Position POSITION_3 = new Position(POSITION_ID_3, POSITION_NAME_3);

    public static final Long POSITION_ID_4 = 4L;
    public static final String POSITION_NAME_4 = "CFO";
    public static final Position POSITION_4 = new Position(POSITION_ID_4, POSITION_NAME_4);

    public static final List<Position> LIST_ALL_POSITIONS = List.of(POSITION_1, POSITION_2, POSITION_3, POSITION_4);


    public static final Long DIVISION_ID_1 = 1L;
    public static final String DIVISION_NAME_1 = "IT";
    public static final Division DIVISION_1 = new Division(DIVISION_ID_1, DIVISION_NAME_1);

    public static final Long DIVISION_ID_2 = 2L;
    public static final String DIVISION_NAME_2 = "Administration";
    public static final Division DIVISION_2 = new Division(DIVISION_ID_2, DIVISION_NAME_2);

    public static final Long DIVISION_ID_3 = 3L;
    public static final String DIVISION_NAME_3 = "Marketing";
    public static final Division DIVISION_3 = new Division(DIVISION_ID_3, DIVISION_NAME_3);

    public static final List<Division> LIST_ALL_DIVISIONS = List.of(DIVISION_1, DIVISION_2);

    public static final Employee EMPLOYEE_1 = new Employee(EMPLOYEE_ID_1, EMPLOYEE_NAME_1, EMPLOYEE_SALARY_1, POSITION_1, DIVISION_1);
    public static final Employee EMPLOYEE_2 = new Employee(EMPLOYEE_ID_2, EMPLOYEE_NAME_2, EMPLOYEE_SALARY_2, POSITION_2, DIVISION_1);
    public static final Employee EMPLOYEE_3 = new Employee(EMPLOYEE_ID_3, EMPLOYEE_NAME_3, EMPLOYEE_SALARY_3, POSITION_3, DIVISION_2);
    public static final Employee EMPLOYEE_4 = new Employee(EMPLOYEE_ID_4, EMPLOYEE_NAME_4, EMPLOYEE_SALARY_4, POSITION_4, DIVISION_2);

    public static final List<Employee> LIST_EMPLOYEES_FOR_POSITION_1 = List.of(EMPLOYEE_1);
    public static final List<Employee> LIST_EMPLOYEES_FOR_POSITION_2 = List.of(EMPLOYEE_2);
    public static final List<Employee> LIST_EMPLOYEES_FOR_POSITION_3 = List.of(EMPLOYEE_3);
    public static final List<Employee> LIST_EMPLOYEES_FOR_POSITION_4 = List.of(EMPLOYEE_4);

    static {
        POSITION_1.setEmployee(LIST_EMPLOYEES_FOR_POSITION_1);
        POSITION_2.setEmployee(LIST_EMPLOYEES_FOR_POSITION_2);
        POSITION_3.setEmployee(LIST_EMPLOYEES_FOR_POSITION_3);
        POSITION_4.setEmployee(LIST_EMPLOYEES_FOR_POSITION_4);
    }

    public static final List<Employee> LIST_EMPLOYEES_FOR_DIVISION_1 = List.of(EMPLOYEE_1, EMPLOYEE_2);
    public static final List<Employee> LIST_EMPLOYEES_FOR_DIVISION_2 = List.of(EMPLOYEE_3, EMPLOYEE_4);

    static {
        DIVISION_1.setEmployee(LIST_EMPLOYEES_FOR_DIVISION_1);
        DIVISION_2.setEmployee(LIST_EMPLOYEES_FOR_DIVISION_2);
    }

    public static final List<Employee> LIST_ALL_EMPLOYEES = List.of(EMPLOYEE_1, EMPLOYEE_2, EMPLOYEE_3, EMPLOYEE_4);

    public static final Long REPORT_ID = 1L;
    public static final String FILE_PATH = "src/main/java/org/ru/skypro/lessons/spring/EmployeeApplication/REPORTS/StatisticDivision_Administration_DT2023-07-05T22:07:59.785349.json";
    public static final Report REPORT = new Report(REPORT_ID, FILE_PATH);

    public static final EmployeeFullInfo EMPLOYEE_FULL_INFO_1 = new EmployeeFullInfo(EMPLOYEE_NAME_1, EMPLOYEE_SALARY_1, POSITION_NAME_1);
    public static final EmployeeFullInfo EMPLOYEE_FULL_INFO_2 = new EmployeeFullInfo(EMPLOYEE_NAME_2, EMPLOYEE_SALARY_2, POSITION_NAME_2);
    public static final EmployeeFullInfo EMPLOYEE_FULL_INFO_3 = new EmployeeFullInfo(EMPLOYEE_NAME_3, EMPLOYEE_SALARY_3, POSITION_NAME_3);
    public static final EmployeeFullInfo EMPLOYEE_FULL_INFO_4 = new EmployeeFullInfo(EMPLOYEE_NAME_4, EMPLOYEE_SALARY_4, POSITION_NAME_4);

    public static final List<EmployeeFullInfo> LIST_ALL_EMPLOYEES_FULL_INFO = List.of(EMPLOYEE_FULL_INFO_1, EMPLOYEE_FULL_INFO_2, EMPLOYEE_FULL_INFO_3, EMPLOYEE_FULL_INFO_4);
    public static final List<EmployeeFullInfo> LIST_ALL_EMPLOYEES_FULL_INFO_FOR_POSITION_1 = List.of(EMPLOYEE_FULL_INFO_1);
    public static final List<EmployeeFullInfo> LIST_ALL_EMPLOYEES_FULL_INFO_FOR_POSITION_2 = List.of(EMPLOYEE_FULL_INFO_2);
    public static final List<EmployeeFullInfo> LIST_ALL_EMPLOYEES_FULL_INFO_FOR_POSITION_3 = List.of(EMPLOYEE_FULL_INFO_3);
    public static final List<EmployeeFullInfo> LIST_ALL_EMPLOYEES_FULL_INFO_FOR_POSITION_4 = List.of(EMPLOYEE_FULL_INFO_4);
    public static final List<EmployeeFullInfo> LIST_ALL_EMPLOYEES_FULL_INFO_FOR_DIVISION_1 = List.of(EMPLOYEE_FULL_INFO_1, EMPLOYEE_FULL_INFO_2);
    public static final List<EmployeeFullInfo> LIST_ALL_EMPLOYEES_FULL_INFO_FOR_DIVISION_2 = List.of(EMPLOYEE_FULL_INFO_3, EMPLOYEE_FULL_INFO_4);

    public static final EmployeeDTO EMPLOYEE_DTO_1 = new EmployeeDTO(EMPLOYEE_ID_1, EMPLOYEE_NAME_1, EMPLOYEE_SALARY_1, DIVISION_ID_1, POSITION_ID_1);
    public static final EmployeeDTO EMPLOYEE_DTO_2 = new EmployeeDTO(EMPLOYEE_ID_2, EMPLOYEE_NAME_2, EMPLOYEE_SALARY_2, DIVISION_ID_1, POSITION_ID_2);
    public static final EmployeeDTO EMPLOYEE_DTO_3 = new EmployeeDTO(EMPLOYEE_ID_3, EMPLOYEE_NAME_3, EMPLOYEE_SALARY_3, DIVISION_ID_2, POSITION_ID_3);
    public static final EmployeeDTO EMPLOYEE_DTO_4 = new EmployeeDTO(EMPLOYEE_ID_4, EMPLOYEE_NAME_4, EMPLOYEE_SALARY_4, DIVISION_ID_2, POSITION_ID_4);

    public static final List<EmployeeDTO> LIST_ALL_EMPLOYEES_DTO = List.of(EMPLOYEE_DTO_1, EMPLOYEE_DTO_2, EMPLOYEE_DTO_3, EMPLOYEE_DTO_4);
    public static final List<EmployeeDTO> LIST_ALL_EMPLOYEES_DTO_FOR_POSITION_1 = List.of(EMPLOYEE_DTO_1);
    public static final List<EmployeeDTO> LIST_ALL_EMPLOYEES_DTO_FOR_POSITION_2 = List.of(EMPLOYEE_DTO_2);
    public static final List<EmployeeDTO> LIST_ALL_EMPLOYEES_DTO_FOR_POSITION_3 = List.of(EMPLOYEE_DTO_3);
    public static final List<EmployeeDTO> LIST_ALL_EMPLOYEES_DTO_FOR_POSITION_4 = List.of(EMPLOYEE_DTO_4);
    public static final List<EmployeeDTO> LIST_ALL_EMPLOYEES_DTO_FOR_DIVISION_1 = List.of(EMPLOYEE_DTO_1, EMPLOYEE_DTO_2);
    public static final List<EmployeeDTO> LIST_ALL_EMPLOYEES_DTO_FOR_DIVISION_2 = List.of(EMPLOYEE_DTO_3, EMPLOYEE_DTO_4);

    public static final int PAGE_INDEX_0 = 0;
    public static final int UNIT_PER_PAGE_1 = 1;

    public static final Page<Employee> PAGE_1 = new PageImpl<>(List.of(EMPLOYEE_1));

    public static final int PAGE_INDEX_1 = 1;
    public static final int UNIT_PER_PAGE_2 = 2;
    public static final Page<Employee> PAGE_2 = new PageImpl<>(List.of(EMPLOYEE_3, EMPLOYEE_4));

    public static final List<EmployeeDTO> LIST_NEW_EMPLOYEES_DTO = List.of(
            new EmployeeDTO(1L, "White Joe", 94686.0, 4L, 14L),
            new EmployeeDTO(2L, "Frazier Amanda", 85850.2, 5L, 16L)
    );

    public static final LocalDateTime LOCAL_DATE_TIME = LocalDate.now().atStartOfDay();
    public static final String FILE_EXTENSION = ".json";
    public static final String PATH = "src/test/java/org/ru/skypro/lessons/spring/EmployeeApplication/constants" +
            "/REPORTS";

    public static final Report NEW_REPORT_ALL = new Report(1L, PATH + "/StatisticDivision__ALL__DT" + LOCAL_DATE_TIME + FILE_EXTENSION);
    public static final Report NEW_REPORT_MARKETING = new Report(2L, PATH + "/StatisticDivision_Marketing_DT" + LOCAL_DATE_TIME + FILE_EXTENSION);
    public static final Path REPORT_ALL = Path.of(NEW_REPORT_ALL.getFilePath());
    public static final Path REPORT_MARKETING = Path.of(NEW_REPORT_MARKETING.getFilePath());

    public static final Path NON_EXISTENT_REPORT = Path.of(PATH,"NoSuchFile");

    public static final String FILE_NAME_REPORT_ALL = String.valueOf(REPORT_ALL.getFileName());
    public static final String FILE_NAME_REPORT_MARKETING = String.valueOf(REPORT_MARKETING.getFileName());
    public static final ReportStatisticsDivision REPORT_STATISTICS_DIVISION_1 = new ReportStatisticsDivision("Marketing",31L,2806023.15,143818.65,50180.26,90516.88);
    public static final ReportStatisticsDivision REPORT_STATISTICS_DIVISION_2 = new  ReportStatisticsDivision("Administration", 6L,696191.82,139628.53,84404.23,116031.97);
    public static final ReportStatisticsDivision REPORT_STATISTICS_DIVISION_3 = new  ReportStatisticsDivision("Finance", 26L,2537096.24,139087.25,51938.23,97580.62);
    public static final ReportStatisticsDivision REPORT_STATISTICS_DIVISION_4 = new  ReportStatisticsDivision("Sales", 11L,1099487.86,140791.39,54499.67,99953.44);
    public static final ReportStatisticsDivision REPORT_STATISTICS_DIVISION_5 = new  ReportStatisticsDivision("IT", 26L,2534634.99,148891.07,50752.18,97485.96);
    public static final List<ReportStatisticsDivision> ALL_DIVISION_REPORT_STATISTICS = List.of(
            REPORT_STATISTICS_DIVISION_1,
            REPORT_STATISTICS_DIVISION_2,
            REPORT_STATISTICS_DIVISION_3,
            REPORT_STATISTICS_DIVISION_4,
            REPORT_STATISTICS_DIVISION_5
    );

    public static Resource RESOURCE_REPORT_MARKETING;

    static {
        try {
            RESOURCE_REPORT_MARKETING = new ByteArrayResource(Files.readAllBytes(REPORT_MARKETING));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static final Resource RESOURCE_REPORT_ALL_DIVISION;

    static {
        try {
            RESOURCE_REPORT_ALL_DIVISION = new ByteArrayResource(Files.readAllBytes(REPORT_ALL));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static final ResponseEntity<Resource> SAVE_REPORT_STATISTICS_MARKETING = ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + FILE_NAME_REPORT_MARKETING + "\"")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .body(RESOURCE_REPORT_MARKETING);

    public static final ResponseEntity<Resource> SAVE_REPORT_STATISTICS_ALL_DIVISIONS = ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + FILE_NAME_REPORT_ALL + "\"")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .body(RESOURCE_REPORT_ALL_DIVISION);

    public static final Exception NO_SUCH_FILE_EXCEPTION = new NoSuchFileException("The report with this id does not exist or the file path is incorrect");



}
