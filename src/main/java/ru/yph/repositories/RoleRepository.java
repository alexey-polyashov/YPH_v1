package ru.yph.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.yph.entities.user.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
}
