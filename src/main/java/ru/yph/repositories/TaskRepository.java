package ru.yph.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yph.entities.task.Task;

import java.sql.Date;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByDateBetween(Date minDate, Date maxDate);
    List<Task> findByDateBetweenAndOwnerIsNull(Date minDate, Date maxDate);
    List<Task> findByDateBetweenAndOwnerIsNotNull(Date minDate, Date maxDate);

}
