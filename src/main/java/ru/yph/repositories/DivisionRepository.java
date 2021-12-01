package ru.yph.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.yph.entities.Division;

import java.util.List;
import java.util.Optional;

@Repository
public interface DivisionRepository extends CrudRepository<Division, Long> {

    //@Query("select d, p.name AS parrent_name from Divisions d Left Join Disision p ON d.parrent = p.id Where d.parrent = ?1")
    List<Division> findByParrent(Division parrent);

    //@Query("select d, p.name AS parrent_name from Division d Left Join Disision p ON d.parrent = p.id Where d.id = ?1")
    Optional<Division> findById(long id);
}
