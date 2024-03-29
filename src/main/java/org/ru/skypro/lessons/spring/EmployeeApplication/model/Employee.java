package org.ru.skypro.lessons.spring.EmployeeApplication.model;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ConditionalOnEnabledResourceChain;

import java.util.Objects;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "employees")
@Data
public class Employee {

    private static final Logger logger = LoggerFactory.getLogger(Employee.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "salary", nullable = false)
    private Double salary;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name="position_id")
    private Position position;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name="division_id")
    private Division division;

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", position=" + position.getPositionName() +
                ", division=" + division.getDivisionName() +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public Employee(Long id, String name, Double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id.equals(employee.id) && name.equals(employee.name) && salary.equals(employee.salary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, salary);
    }
}
