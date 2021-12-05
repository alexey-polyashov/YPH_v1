package ru.yph.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.yph.dto.task.NewTaskDTO;
import ru.yph.dto.task.TaskDTO;
import ru.yph.entities.task.Task;
import ru.yph.exceptions.ResourceNotFoundException;
import ru.yph.service.TaskService;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "api/v21/tasks")
@Slf4j
public class TaskController {

    private final ModelMapper myModelMapper;
    private final TaskService taskService;

    @GetMapping(value = "/{id}")
    public TaskDTO getByID(@PathVariable long id){
        Task task = taskService.findById(id).orElseThrow(()->new ResourceNotFoundException("Task with id '" + id + "' not found!"));
        TaskDTO tasksDTO= myModelMapper.map(
                task,
                TaskDTO.class
        );
        return tasksDTO;
    }

    @GetMapping(value = "/interval")
    public List<TaskDTO> getByInterval(@RequestParam Date startDate, @RequestParam Date endDate){
        List<Task> tasks = taskService.findByDateBetween(startDate, endDate);
        List<TaskDTO> tasksDTO = tasks.stream().map(
                (e)->myModelMapper.map(e, TaskDTO.class)
        ).collect(Collectors.toList());
        return tasksDTO;
    }

    @GetMapping(value = "/interval/common")
    public List<TaskDTO> getCommonByInterval(@RequestParam Date startDate, @RequestParam Date endDate){
        List<Task> tasks = taskService.findByDateBetweenAndOwnerIsNull(startDate, endDate);
        List<TaskDTO> tasksDTO = tasks.stream().map(
                (e)->myModelMapper.map(e, TaskDTO.class)
        ).collect(Collectors.toList());
        return tasksDTO;
    }

    @GetMapping(value = "/interval/personally")
    public List<TaskDTO> getPersonallyByInterval(@RequestParam Date startDate, @RequestParam Date endDate){
        List<Task> tasks = taskService.findByDateBetweenAndOwnerIsNotNull(startDate, endDate);
        List<TaskDTO> tasksDTO = tasks.stream().map(
                (e)->myModelMapper.map(e, TaskDTO.class)
        ).collect(Collectors.toList());
        return tasksDTO;
    }

    @PostMapping(value = "/")
    public void addTask(@RequestBody NewTaskDTO newTask){
        taskService.addTask(newTask);
    }

    @DeleteMapping(value = "/{id}")
    public void delTask(@PathVariable long id){
        taskService.delTask(id);
    }



}
