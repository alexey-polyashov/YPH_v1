package ru.yph.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.yph.entities.task.Task;

import java.sql.Date;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {

    List<Task> findByInitionDateBetween(Date minDate, Date maxDate);
    List<Task> findByInitionDateBetweenAndCommonIsTrue(Date minDate, Date maxDate);
    List<Task> findByInitionDateBetweenAndCommonIsFalse(Date minDate, Date maxDate);

}
