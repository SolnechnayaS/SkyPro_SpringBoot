package org.ru.skypro.lessons.spring.EmployeeApplication.model.projections;

import org.springframework.beans.factory.annotation.Value;

public interface EmployeeInfo {
    @Value("#{target.name + ' ' + target.salary}")
    String getFullInfo();
}
