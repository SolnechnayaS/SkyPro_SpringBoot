package org.ru.skypro.lessons.spring.EmployeeApplication.model;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Integer id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "salary", nullable = false)
    private int salary;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name="position_id")
    private Position position;

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", Имя='" + name + '\'' +
                ", Зарплата=" + salary +
                '}';
    }
}
