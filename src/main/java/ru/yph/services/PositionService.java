package ru.yph.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yph.entities.Position;
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
