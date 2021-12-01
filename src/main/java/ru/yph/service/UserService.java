package ru.yph.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.yph.entities.Division;
import ru.yph.entities.User;
import ru.yph.exceptions.ResourceNotFoundException;
import ru.yph.repositories.DivisionRepository;
import ru.yph.repositories.UserRepository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findById(long id){
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with id '" + id + "' not found!"));
    }
    public User findByLogin(String login){
        return userRepository.findByLogin(login).orElseThrow(() -> new ResourceNotFoundException("User with login '" + login + "' not found!"));
    }
    public User getUserData(Principal principal){
        String login = principal.getName();
        return userRepository.findByLogin(login).orElseThrow(() -> new ResourceNotFoundException("User not found in data base!"));
    }
    public User findByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found in data base!"));
    }

    public Page<User> findAll(int page, int recordsOnPage) {
        return userRepository.findAll(PageRequest.of(page, recordsOnPage));
    }
}
