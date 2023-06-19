package org.ru.skypro.lessons.spring.EmployeeApplication.repository;

import org.ru.skypro.lessons.spring.EmployeeApplication.model.Position;
import org.springframework.data.repository.CrudRepository;

public interface PositionRepository extends CrudRepository<Position, Integer> {
    Position findByPositionName(String positionName);

    Position findByPositionId(Long positionId);
}
