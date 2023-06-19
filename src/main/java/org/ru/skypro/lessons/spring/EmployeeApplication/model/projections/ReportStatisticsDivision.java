package org.ru.skypro.lessons.spring.EmployeeApplication.model.projections;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportStatisticsDivision {

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
