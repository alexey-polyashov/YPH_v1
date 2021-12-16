package ru.yph.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yph.entities.task.TaskFile;

@Repository
public interface TaskFilesRepository extends JpaRepository<TaskFile, Long> {
}
