package org.ru.skypro.lessons.spring.EmployeeApplication.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "division")
public class Division {

    private static final Logger logger = LoggerFactory.getLogger(Division.class);
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "division_id")
    private Long divisionId;

    @Column(name = "division_name", length = 100, nullable = false)
    String divisionName;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "division")
    private List<Employee> employee;

    public Division(Long divisionId, String divisionName) {
        this.divisionId = divisionId;
        this.divisionName = divisionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Division division = (Division) o;
        return divisionId.equals(division.divisionId) && divisionName.equals(division.divisionName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(divisionId, divisionName);
    }
}
