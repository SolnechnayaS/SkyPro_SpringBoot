package org.ru.skypro.lessons.spring.EmployeeApplication.service;
import org.ru.skypro.lessons.spring.EmployeeApplication.model.Position;

public interface PositionService {

Position getPositionByPositionName (String positionName);
    void deletePositionById (int id);
    void addPosition(Position position);
}
