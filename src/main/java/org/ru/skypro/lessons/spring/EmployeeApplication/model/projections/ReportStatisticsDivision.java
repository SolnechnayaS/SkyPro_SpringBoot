package org.ru.skypro.lessons.spring.EmployeeApplication.model.projections;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Division;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportStatisticsDivision {

    private static final Logger logger = LoggerFactory.getLogger(ReportStatisticsDivision.class);

    //    Название отдела.
    String divisionName;

    //    Количество сотрудников.
    Long numberEmployees;

    //    Суммарная зарплата.
    Double sumSalary;

    //    Максимальная зарплата.
    Double maxSalary;

    //    Минимальная зарплата.
    Double minSalary;
    //    Средняя зарплата.
    Double averageSalary;
}
