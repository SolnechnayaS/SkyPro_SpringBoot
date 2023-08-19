package org.ru.skypro.lessons.spring.EmployeeApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Employee;

@Data
@NoArgsConstructor
@AllArgsConstructor

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
        else {employeeDTO.setDivisionId(null);}

        if (employee.getPosition() !=null)
        {employeeDTO.setPositionId(employee.getPosition().getPositionId());}
        else {employeeDTO.setPositionId(null);}

        return employeeDTO;
    }
    
}
