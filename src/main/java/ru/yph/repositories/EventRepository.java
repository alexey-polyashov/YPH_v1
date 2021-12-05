package ru.yph.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yph.entities.event.Event;

import java.sql.Date;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByDateBetween(Date minDate, Date maxDate);
    List<Event> findByDateBetweenAndOwnerIsNull(Date minDate, Date maxDate);
    List<Event> findByDateBetweenAndOwnerIsNotNull(Date minDate, Date maxDate);

}
