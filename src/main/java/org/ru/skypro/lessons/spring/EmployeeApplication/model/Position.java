package org.ru.skypro.lessons.spring.EmployeeApplication.model;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "position")
public class Position {

    private static final Logger logger = LoggerFactory.getLogger(Position.class);
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "position_id")
    private Long positionId;

    @Column(name = "position_name", length = 100, nullable = false)
    String positionName;
    @OneToMany (fetch =FetchType.EAGER, mappedBy = "position")
    private List<Employee> employee;

    public Position(Long positionId, String positionName) {
        this.positionId = positionId;
        this.positionName = positionName;
    }
}
