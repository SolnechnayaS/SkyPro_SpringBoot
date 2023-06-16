package org.ru.skypro.lessons.spring.EmployeeApplication.repository;

import org.ru.skypro.lessons.spring.EmployeeApplication.model.Division;
import org.springframework.data.repository.CrudRepository;

public interface DivisionRepository extends CrudRepository<Division, Integer> {
    Division findByDivisionName(String divisionName);
    Division findByDivisionId(Integer divisionId);


}
