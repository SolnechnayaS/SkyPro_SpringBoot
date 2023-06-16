package org.ru.skypro.lessons.spring.EmployeeApplication.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "division")
public class Division {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "division_id")
    private Integer divisionId;

    @Column(name = "division_name", length = 100, nullable = false)
    String divisionName;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "division")
    private List<Employee> employee;

}
