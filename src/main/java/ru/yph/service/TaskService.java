package ru.yph.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yph.dto.task.NewTaskDTO;
import ru.yph.entities.task.Task;
import ru.yph.exceptions.ResourceNotFoundException;
import ru.yph.repositories.TaskRepository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final ModelMapper myModelMapper;

    public Optional<Task> findById(long id){
        return taskRepository.findById(id);
    }

    @Transactional
    public List<Task> findByDateBetween(Date minDate, Date maxDate) {
        return taskRepository.findByDateBetween(minDate, maxDate);
    }

    @Transactional
    public List<Task> findByDateBetweenAndOwnerIsNull(Date minDate, Date maxDate) {
        return taskRepository.findByDateBetweenAndOwnerIsNull(minDate, maxDate);
    }

    @Transactional
    public List<Task> findByDateBetweenAndOwnerIsNotNull(Date minDate, Date maxDate) {
        return taskRepository.findByDateBetweenAndOwnerIsNotNull(minDate, maxDate);
    }

    public void addTask(NewTaskDTO newTask) {
        Task task = myModelMapper.map(newTask, Task.class);
        taskRepository.save(task);
    }

    public void delTask(long id) {
        Task task = findById(id).orElseThrow(()->new ResourceNotFoundException("Event with id '" + id + "' not found!"));
        taskRepository.delete(task);
    }

}
