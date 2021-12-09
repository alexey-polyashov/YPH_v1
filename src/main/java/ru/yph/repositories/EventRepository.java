package ru.yph.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yph.entities.event.Event;

import java.sql.Date;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByInitionDateBetween(Date minDate, Date maxDate);
    List<Event> findByInitionDateBetweenAndOwnerIsNull(Date minDate, Date maxDate);
    List<Event> findByInitionDateBetweenAndOwnerIsNotNull(Date minDate, Date maxDate);

}
