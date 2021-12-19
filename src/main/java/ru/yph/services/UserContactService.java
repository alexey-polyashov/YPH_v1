package ru.yph.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yph.entities.user.UserContact;
import ru.yph.repositories.UserContactsRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserContactService {

    private final UserContactsRepository userContactsRepository;

    public Optional<UserContact> findById(Long id) {
        return userContactsRepository.findById(id);
    }
}
