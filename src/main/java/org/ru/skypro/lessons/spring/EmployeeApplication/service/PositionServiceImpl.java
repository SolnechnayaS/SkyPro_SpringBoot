package org.ru.skypro.lessons.spring.EmployeeApplication.service;

import org.ru.skypro.lessons.spring.EmployeeApplication.model.Position;
import org.ru.skypro.lessons.spring.EmployeeApplication.repository.PositionRepository;

public class PositionServiceImpl implements PositionService {

    private final PositionRepository positionRepository;

    public PositionServiceImpl(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    @Override
    public Position getPositionByPositionName(String positionName) {
        return positionRepository.findByPositionName(positionName);
    }

    @Override
    public void deletePositionById(int positionId) {
        positionRepository.deleteById(positionId);
    }

    @Override
    public void addPosition(Position position) {
        positionRepository.save(position);
    }
}
