package ru.yph.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.yph.dto.task.FullTaskDTO;
import ru.yph.dto.task.NewFullTaskDTO;
import ru.yph.dto.task.NewTaskDTO;
import ru.yph.dto.task.TaskDTO;
import ru.yph.dto.user.NewUserDTO;
import ru.yph.dto.user.UserDTO;
import ru.yph.entities.task.Task;
import ru.yph.entities.user.User;
import ru.yph.exceptions.ResourceNotFoundException;
import ru.yph.mappers.UserMapper;
import ru.yph.service.TaskService;
import ru.yph.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.sql.Date;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequiredArgsConstructor
@RequestMapping(value = "api/v21/tasks")
@Slf4j
public class TasksController {

    private final ModelMapper myModelMapper;
    private final TaskService taskService;


    @GetMapping(value = "/tasklistshort")
    @ResponseBody
    public Page<TaskDTO> tasksList(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int recordsOnPage){
        Page<Task> tasks = taskService.findAll(page, recordsOnPage);
        Page<TaskDTO> taskDtoPage = tasks.map(p->new TaskDTO(p));
        return taskDtoPage;
    }

    @GetMapping(value = "/tasklist")
    @ResponseBody
    public Page<FullTaskDTO> fullTasksList(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int recordsOnPage){
        Page<Task> tasks = taskService.findAll(page, recordsOnPage);
        Page<FullTaskDTO> taskDtoPage = tasks.map(p->new FullTaskDTO(p));
        return taskDtoPage;
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public TaskDTO getByID(@PathVariable long id){
        Task task = taskService.findById(id).orElseThrow(()->new ResourceNotFoundException("Task with id '" + id + "' not found!"));
        TaskDTO tasksDTO= new TaskDTO(task);
        return tasksDTO;
    }

    @GetMapping(value = "/whole/{id}")
    @ResponseBody
    public FullTaskDTO getFullByID(@PathVariable long id){
        Task task = taskService.findById(id).orElseThrow(()->new ResourceNotFoundException("Task with id '" + id + "' not found!"));
        FullTaskDTO tasksDTO= new FullTaskDTO(task);
        return tasksDTO;
    }

    @GetMapping(value = "/interval")
    @ResponseBody
    public List<TaskDTO> getByInterval(@RequestParam Date startDate, @RequestParam Date endDate){
        List<Task> tasks = taskService.findByDateBetween(startDate, endDate);
        List<TaskDTO> tasksDTO = tasks.stream().map(
                (e)->new TaskDTO(e)
        ).collect(Collectors.toList());
        return tasksDTO;
    }

    @GetMapping(value = "/interval/common")
    @ResponseBody
    public List<TaskDTO> getCommonByInterval(@RequestParam Date startDate, @RequestParam Date endDate){
        List<Task> tasks = taskService.findByDateBetweenAndOwnerIsNull(startDate, endDate);
        List<TaskDTO> tasksDTO = tasks.stream().map(
                (e)->new TaskDTO(e)
        ).collect(Collectors.toList());
        return tasksDTO;
    }

    @GetMapping(value = "/interval/personally")
    @ResponseBody
    public List<TaskDTO> getPersonallyByInterval(@RequestParam Date startDate, @RequestParam Date endDate){
        List<Task> tasks = taskService.findByDateBetweenAndOwnerIsNotNull(startDate, endDate);
        List<TaskDTO> tasksDTO = tasks.stream().map(
                (e)->new TaskDTO(e)
        ).collect(Collectors.toList());
        return tasksDTO;
    }

    @PostMapping(value = "/")
    @ResponseBody
    public void addTask( @Valid @RequestBody NewTaskDTO newTask) throws ParseException {
        taskService.addTask(newTask);
    }

    @PostMapping(value = "/whole/")
    @ResponseBody
    public void addTask( @Valid @RequestBody NewFullTaskDTO newTask) throws ParseException {
        taskService.addFullTask(newTask);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public void delTask(@PathVariable long id){
        taskService.delTask(id);
    }


}
