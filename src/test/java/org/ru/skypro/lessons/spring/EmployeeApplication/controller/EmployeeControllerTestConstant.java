package org.ru.skypro.lessons.spring.EmployeeApplication.controller;

import lombok.Data;
import org.ru.skypro.lessons.spring.EmployeeApplication.dto.EmployeeDTO;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Division;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Position;

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
    public static final String POSITION_NAME_1 = "Position1";
    public static final Position POSITION_1 = new Position(POSITION_ID_1, POSITION_NAME_1);

    public static final Long POSITION_ID_2 = 2L;
    public static final String POSITION_NAME_2 = "Position2";
    public static final Position POSITION_2 = new Position(POSITION_ID_2, POSITION_NAME_2);

    public static final Long POSITION_ID_3 = 3L;
    public static final String POSITION_NAME_3 = "Position3";
    public static final Position POSITION_3 = new Position(POSITION_ID_3, POSITION_NAME_3);

    public static final Long POSITION_ID_4 = 4L;
    public static final String POSITION_NAME_4 = "Position4";
    public static final Position POSITION_4 = new Position(POSITION_ID_4, POSITION_NAME_4);

    public static final Long DIVISION_ID_1 = 1L;
    public static final String DIVISION_NAME_1 = "Division1";
    public static final Division DIVISION_1 = new Division(DIVISION_ID_1, DIVISION_NAME_1);

    public static final Long DIVISION_ID_2 = 2L;
    public static final String DIVISION_NAME_2 = "Division2";
    public static final Division DIVISION_2 = new Division(DIVISION_ID_2, DIVISION_NAME_2);

    public static final EmployeeDTO EMPLOYEE_DTO_1 = new EmployeeDTO(EMPLOYEE_ID_1,EMPLOYEE_NAME_1,EMPLOYEE_SALARY_1,DIVISION_ID_1,POSITION_ID_1);
    public static final EmployeeDTO EMPLOYEE_DTO_2 = new EmployeeDTO(EMPLOYEE_ID_2,EMPLOYEE_NAME_2,EMPLOYEE_SALARY_2,DIVISION_ID_1,POSITION_ID_2);
    public static final EmployeeDTO EMPLOYEE_DTO_3 = new EmployeeDTO(EMPLOYEE_ID_3,EMPLOYEE_NAME_3,EMPLOYEE_SALARY_3,DIVISION_ID_2,POSITION_ID_3);
    public static final EmployeeDTO EMPLOYEE_DTO_4 = new EmployeeDTO(EMPLOYEE_ID_4,EMPLOYEE_NAME_4,EMPLOYEE_SALARY_4,DIVISION_ID_2,POSITION_ID_4);

}
