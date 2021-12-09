package ru.yph.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yph.entities.user.AddressType;
import ru.yph.exceptions.ResourceNotFoundException;
import ru.yph.repositories.AddressTypeRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressTypeService {
    public AddressTypeRepository addressTypeRepository;
    public Optional<AddressType> findById(Long id){
        return addressTypeRepository.findById(id);
    }
}
