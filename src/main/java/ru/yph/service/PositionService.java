package ru.yph.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yph.entities.Division;
import ru.yph.entities.Position;
import ru.yph.exceptions.ResourceNotFoundException;
import ru.yph.repositories.DivisionRepository;
import ru.yph.repositories.PositionRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PositionService {

    private final PositionRepository positionRepository;

    public Optional<Position> findById(long id){
        return positionRepository.findById(id);
    }

    public List<Position> findAll() {
        return positionRepository.findAllByOrderByNameDesc();
    }
}
