package ru.yph.services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yph.dtos.task.NewFullTaskDTO;
import ru.yph.dtos.task.NewTaskDTO;
import ru.yph.entities.task.Task;
import ru.yph.entities.task.TaskExecutor;
import ru.yph.exceptions.ResourceNotFoundException;
import ru.yph.repositories.TaskExecutorRepository;
import ru.yph.repositories.TaskRepository;
import java.sql.Date;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;
    private final TaskFileService fileService;
    private final TaskExecutorRepository taskExecutorRepository;
    private final ModelMapper myModelMapper;

    public Optional<Task> findById(long id){
        return taskRepository.findById(id);
    }

    @Transactional
    public List<Task> findByDateBetween(Date minDate, Date maxDate) {
        return taskRepository.findByInitionDateBetween(minDate, maxDate);
    }

    @Transactional
    public List<Task> findByDateBetweenAndOwnerIsNull(Date minDate, Date maxDate) {
        return taskRepository.findByInitionDateBetweenAndCommonIsTrue(minDate, maxDate);
    }

    @Transactional
    public List<Task> findByDateBetweenAndOwnerIsNotNull(Date minDate, Date maxDate) {
        return taskRepository.findByInitionDateBetweenAndCommonIsFalse(minDate, maxDate);
    }

    public void addTask(NewTaskDTO newTask) throws ParseException {
        Task task = newTask.createTask(userService, this);
        taskRepository.save(task);
    }

    @Transactional
    public void addFullTask(NewFullTaskDTO newTask) throws ParseException {
        Task task = newTask.createTask(userService, this, fileService);
        taskRepository.save(task);
        for (TaskExecutor te: task.getTaskExecutors()) {
            taskExecutorRepository.save(te);
        }
    }

    public void delTask(long id) {
        Task task = findById(id).orElseThrow(()->new ResourceNotFoundException("Event with id '" + id + "' not found!"));
        taskRepository.delete(task);
    }

    public Page<Task> findAll(int page, int recordsOnPage) {
        return taskRepository.findAll(PageRequest.of(page, recordsOnPage));
    }

    public Page<Task> findAllwithFilter(int page, int recordsOnPage, Specification spec) {
        return taskRepository.findAll(spec, PageRequest.of(page, recordsOnPage));
    }


    public Optional<TaskExecutor> findExecutorById(Long id){
        return taskExecutorRepository.findById(id);
    }

}
