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
@Table (name = "position")
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "position_id")
    private Long positionId;

    @Column(name = "position_name", length = 100, nullable = false)
    String positionName;
    @OneToMany (fetch =FetchType.EAGER, mappedBy = "position")
    private List<Employee> employee;

}
