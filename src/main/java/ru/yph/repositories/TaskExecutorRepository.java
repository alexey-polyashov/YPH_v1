package ru.yph.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yph.entities.task.Task;
import ru.yph.entities.task.TaskExecutor;

import java.sql.Date;
import java.util.List;

@Repository
public interface TaskExecutorRepository extends JpaRepository<TaskExecutor, Long> {


}
