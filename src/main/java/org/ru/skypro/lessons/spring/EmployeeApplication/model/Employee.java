package org.ru.skypro.lessons.spring.EmployeeApplication.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Employee {

    private static int idGenerator = 1;
    private int id;
    private String name;
    private int salary;

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", Имя='" + name + '\'' +
                ", Зарплата=" + salary +
                '}';
    }

    public Employee(String name, int salary) {
        this.id = idGenerator;
        idGenerator++;
        this.name = name;
        this.salary = salary;
    }
}
