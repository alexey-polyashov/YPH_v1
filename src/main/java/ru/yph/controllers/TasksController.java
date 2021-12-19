package ru.yph.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.yph.dtos.SearchCriteria;
import ru.yph.dtos.SearchCriteriaDTO;
import ru.yph.dtos.TaskSearchCriteriaDTO;
import ru.yph.dtos.task.FullTaskDTO;
import ru.yph.dtos.task.NewFullTaskDTO;
import ru.yph.dtos.task.NewTaskDTO;
import ru.yph.dtos.task.TaskDTO;
import ru.yph.entities.task.Task;
import ru.yph.exceptions.NotValidFields;
import ru.yph.exceptions.ResourceNotFoundException;
import ru.yph.exceptions.Violation;
import ru.yph.repositories.specifications.TaskSpecificationBuilder;
import ru.yph.services.TaskService;
import ru.yph.services.UserService;

import javax.validation.Valid;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


@Controller
@RequiredArgsConstructor
@RequestMapping(value = "api/v21/tasks")
@Slf4j
public class TasksController {

    private final ModelMapper myModelMapper;
    private final TaskService taskService;
    private final UserService userService;


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

    @PostMapping(value = "/tasklist/filter")
    @ResponseBody
    public Page<FullTaskDTO> fullTasksList(@RequestBody TaskSearchCriteriaDTO requestDTO){
        List<SearchCriteriaDTO> filter = requestDTO.getFilter();
        Integer page = requestDTO.getPage();
        Integer recordsOnPage = requestDTO.getRecordsOnPage();
        TaskSpecificationBuilder builder = new TaskSpecificationBuilder(userService);
        for(SearchCriteriaDTO creteriaDTO: filter) {
            Object value = creteriaDTO.getValue();
            builder.with(creteriaDTO.getKey(), creteriaDTO.getOperation(), value);
        }
        Specification<Task> spec = builder.build();
        Page<Task> tasks = taskService.findAllwithFilter(page, recordsOnPage, spec);
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
