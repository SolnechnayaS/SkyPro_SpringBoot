package org.ru.skypro.lessons.spring.EmployeeApplication.controller;

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

@Data
public class EmployeeControllerTestConstant {
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

    public static final Employee EMPLOYEE_1 = new Employee(EMPLOYEE_ID_1, EMPLOYEE_NAME_1, EMPLOYEE_SALARY_1
            , POSITION_1, DIVISION_1
    );
    public static final Employee EMPLOYEE_2 = new Employee(EMPLOYEE_ID_2, EMPLOYEE_NAME_2, EMPLOYEE_SALARY_2
            , POSITION_2, DIVISION_1
    );
    public static final Employee EMPLOYEE_3 = new Employee(EMPLOYEE_ID_3, EMPLOYEE_NAME_3, EMPLOYEE_SALARY_3
            , POSITION_3, DIVISION_2
    );
    public static final Employee EMPLOYEE_4 = new Employee(EMPLOYEE_ID_4, EMPLOYEE_NAME_4, EMPLOYEE_SALARY_4
            , POSITION_4, DIVISION_2
    );

    public static final List<Employee> LIST_EMPLOYEES_FOR_POSITION_1 = List.of(EMPLOYEE_1);
    public static final List<Employee> LIST_EMPLOYEES_FOR_POSITION_2 = List.of(EMPLOYEE_2);
    public static final List<Employee> LIST_EMPLOYEES_FOR_POSITION_3 = List.of(EMPLOYEE_3);
    public static final List<Employee> LIST_EMPLOYEES_FOR_POSITION_4 = List.of(EMPLOYEE_4);

    public static final List<Employee> LIST_EMPLOYEES_FOR_DIVISION_1 = List.of(EMPLOYEE_1, EMPLOYEE_2);
    public static final List<Employee> LIST_EMPLOYEES_FOR_DIVISION_2 = List.of(EMPLOYEE_3, EMPLOYEE_4);

//    static {
//        POSITION_1.setEmployee(LIST_EMPLOYEES_FOR_POSITION_1);
//        POSITION_2.setEmployee(LIST_EMPLOYEES_FOR_POSITION_2);
//        POSITION_3.setEmployee(LIST_EMPLOYEES_FOR_POSITION_3);
//        POSITION_4.setEmployee(LIST_EMPLOYEES_FOR_POSITION_4);
//    }
//
//    static {
//        DIVISION_1.setEmployee(LIST_EMPLOYEES_FOR_DIVISION_1);
//        DIVISION_2.setEmployee(LIST_EMPLOYEES_FOR_DIVISION_2);
//    }
//
//    static {
//        EMPLOYEE_1.setPosition(POSITION_1);
//        EMPLOYEE_1.setDivision(DIVISION_1);
//        EMPLOYEE_2.setPosition(POSITION_2);
//        EMPLOYEE_2.setDivision(DIVISION_1);
//        EMPLOYEE_3.setPosition(POSITION_3);
//        EMPLOYEE_3.setDivision(DIVISION_2);
//        EMPLOYEE_4.setPosition(POSITION_4);
//        EMPLOYEE_4.setDivision(DIVISION_2);
//    }

    public static final List<Employee> LIST_ALL_EMPLOYEES = List.of(EMPLOYEE_1, EMPLOYEE_2, EMPLOYEE_3, EMPLOYEE_4);

}
