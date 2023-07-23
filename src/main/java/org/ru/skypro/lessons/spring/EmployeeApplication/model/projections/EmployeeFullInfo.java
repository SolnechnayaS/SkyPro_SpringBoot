package org.ru.skypro.lessons.spring.EmployeeApplication.model.projections;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.ru.skypro.lessons.spring.EmployeeApplication.dto.EmployeeDTO;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Employee;
import org.ru.skypro.lessons.spring.EmployeeApplication.service.EmployeeService;
import org.ru.skypro.lessons.spring.EmployeeApplication.service.EmployeeServiceImpl;
import org.springframework.data.repository.CrudRepository;

@Data
@AllArgsConstructor
public class EmployeeFullInfo {

    private String name;
    private Double salary;
    private String positionName;

    public static EmployeeFullInfo fromEmployee(Employee employee) {
        return new EmployeeFullInfo(employee.getName(), employee.getSalary(), employee.getPosition().getPositionName());
    }


}
