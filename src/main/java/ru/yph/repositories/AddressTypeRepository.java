package ru.yph.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yph.entities.user.AddressType;

public interface AddressTypeRepository extends JpaRepository<AddressType, Long> {

}
