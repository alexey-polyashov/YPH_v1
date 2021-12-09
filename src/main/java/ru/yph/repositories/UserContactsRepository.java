package ru.yph.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yph.entities.user.UserContact;

public interface UserContactsRepository extends JpaRepository<UserContact, Long> {
}
