package ru.yph.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yph.entities.Division;
import ru.yph.exceptions.ResourceNotFoundException;
import ru.yph.repositories.DivisionRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DivisionService {

    private final DivisionRepository divisionRepository;

    public List<Division> findOnParrent(long parrentId){
        Division d = findById(parrentId).orElseThrow(() -> new ResourceNotFoundException("Division with id '" + parrentId + "' not found!"));
        return divisionRepository.findByParrent(d);
    }
    public Optional<Division> findById(long id){
        return divisionRepository.findById(id);
    }
    public List<Division> findAll(){
        return divisionRepository.findAll();
    }
    public List<Division> findAllOrderList(){
        return divisionRepository.findAllByOrderByLeftMargin();
    }

    public void delDivision(Long id) {
        Division division = divisionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Division with id '" + id + "' not found!"));
        divisionRepository.delete(division);
    }
}
