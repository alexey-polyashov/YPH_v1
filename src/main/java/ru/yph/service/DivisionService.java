package ru.yph.service;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yph.entities.Division;
import ru.yph.exceptions.ResourceNotFoundException;
import ru.yph.repositories.DivisionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DivisionService {

    private final DivisionRepository divisionRepository;

    public List<Division> findOnParrent(long parrentId){
        Division d = findById(parrentId);

        return divisionRepository.findByParrent(d);
    }
    public Division findById(long id){
        return divisionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Division with id '" + id + "' not found!"));
    }
}
