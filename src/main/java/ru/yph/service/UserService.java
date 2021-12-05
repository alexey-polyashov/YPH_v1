package ru.yph.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yph.dto.user.NewUserDTO;
import ru.yph.entities.user.User;
import ru.yph.exceptions.ResourceNotFoundException;
import ru.yph.repositories.UserRepository;

import java.security.Principal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper myModelMapper;

    public Optional<User> findById(long id){
        return userRepository.findById(id);
    }
    public Optional<User> findByLogin(String login){
        return userRepository.findByLogin(login);
    }

    public Optional<User> getUserData(Principal principal){
        String login = principal.getName();
        return userRepository.findByLogin(login);
    }
    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Transactional
    public Page<User> findAll(int page, int recordsOnPage) {
        return userRepository.findAll(PageRequest.of(page, recordsOnPage));
    }

    public void addUser(NewUserDTO newUserData) {
        User userData = myModelMapper.map(
                newUserData
                ,User.class);
        userRepository.save(userData);
    }

    @Transactional
    private void setImage(Long id, String imgPath){
        User userData = findById(id).orElseThrow(()->new ResourceNotFoundException("User with id '" + id + "' not found!"));
        userData.setImage(imgPath);
        userRepository.save(userData);
    }

    public void delUser(Long id) {
        User userData = findById(id).orElseThrow(()->new ResourceNotFoundException("User with id '" + id + "' not found!"));
        userRepository.delete(userData);

    }
}
