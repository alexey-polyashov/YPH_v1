package ru.yph.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yph.entities.user.User;


@Repository
public interface UserWithContacts extends JpaRepository<User, Long> {
}
