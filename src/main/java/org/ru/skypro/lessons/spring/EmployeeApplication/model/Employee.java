package org.ru.skypro.lessons.spring.EmployeeApplication.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Employee {

    private String name;
    private int salary;

    @Override
    public String toString() {
        return "{" +
                "Имя='" + name + '\'' +
                ", Зарплата=" + salary +
                '}';
    }
}
