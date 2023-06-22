package org.ru.skypro.lessons.spring.EmployeeApplication.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Employee;

@Data
@NoArgsConstructor
public class EmployeeDTO {
    private Long id;
    private String name;
    private Double salary;
    private Long divisionId;
    private Long positionId;

    public static EmployeeDTO fromEmployee(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setSalary(employee.getSalary());

        if (employee.getDivision() !=null)
        {employeeDTO.setDivisionId(employee.getDivision().getDivisionId());}

        if (employee.getPosition() !=null)
        {employeeDTO.setPositionId(employee.getPosition().getPositionId());}
        return employeeDTO;
    }

    public Employee toEmployee() {
        Employee employee = new Employee();
        employee.setId(this.getId());
        employee.setName(this.getName());
        employee.setSalary(this.getSalary());

        return employee;
    }
    
}
