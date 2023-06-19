package org.ru.skypro.lessons.spring.EmployeeApplication.repository;

import org.ru.skypro.lessons.spring.EmployeeApplication.dto.DivisionDTO;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Division;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DivisionRepository extends CrudRepository<Division, Integer> {
    Division findByDivisionName(String divisionName);

    Division findByDivisionId(Long divisionId);

    @Query(value = "SELECT division_id FROM division",
            nativeQuery = true)
    List<Long> allDivisionId();

}
