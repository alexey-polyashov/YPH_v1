package ru.yph.dto.task;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.yph.dto.DateValidator;
import ru.yph.dto.DurationValidator;
import ru.yph.dto.TimeValidator;
import ru.yph.entities.task.Task;
import ru.yph.entities.task.TaskExecutor;
import ru.yph.entities.task.TaskFile;
import ru.yph.entities.user.User;
import ru.yph.exceptions.NotValidFields;
import ru.yph.exceptions.ResourceNotFoundException;
import ru.yph.exceptions.Violation;
import ru.yph.service.TaskFileService;
import ru.yph.service.TaskService;
import ru.yph.service.UserService;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
public class NewFullTaskDTO {

    private Long id; //если указан, то это редактирование
    @NotNull(message="Не указано краткое описание задачи")
    private String shortDescribe;
    private String fullDescribe;
    private Boolean repeatable;
    private String repeatPeriod;
    @NotNull(message="Не указан автор задачи")
    @Min(value=1, message="Не указан автор задачи")
    private Long authorId;
    @DurationValidator
    private String durationOfExecute;
    @NotNull(message="Не указана дата постановки задачи")
    @DateValidator
    private String initionDate;
    @NotNull(message="Не указано время постановки задачи")
    @TimeValidator
    private String initionTime;
    private Boolean common;
    private Boolean active;
    private List<TaskFileDTO> taskFiles;
    private List<NewTaskExecutorDTO> taskExecutors;

    public Task createTask(UserService userService, TaskService taskService, TaskFileService fileService) throws ParseException {

        Task task = new Task();

        Long id = this.getId();
        if(id!=null && id!=0){
            task = taskService.findById(id).orElseThrow(()->new ResourceNotFoundException("Задача с id '" + id + "' не найдена!"));
        }

        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormater = new SimpleDateFormat("HH:MM");

        task.setShortDescribe(this.getShortDescribe());
        task.setFullDescribe(this.getFullDescribe());
        task.setRepeatable(this.getRepeatable());
        if(this.repeatable){
            if(this.repeatable){
                try {
                    Duration d = Duration.parse(this.getRepeatPeriod());
                    task.setRepeatPeriod(d);
                }catch(DateTimeParseException e) {
                    throw new NotValidFields(new ArrayList<>(Arrays.asList(new Violation("repeatPeriod","Не верный формат периода"))));
                }
            }
        }
        Long authorId = this.getAuthorId();
        if(authorId!=null && authorId>0) {
            User author = userService.findById(authorId).orElseThrow(()->new ResourceNotFoundException("Пользователь с id '" + authorId + "' не найдена!"));
            task.setAuthor(author);
        }
        task.setCommon(this.getCommon());
        task.setDurationOfExecute(Duration.parse(this.getDurationOfExecute()));
        Date dateVal = new Date(dateFormater.parse(this.getInitionDate()).getTime());
        Time timeVal = new Time(timeFormater.parse(this.getInitionTime()).getTime());
        task.setInitionDate(dateVal);
        task.setInitionTime(timeVal);
        task.setActive(this.getActive());

        List<TaskExecutor> executors = task.getTaskExecutors();
        if(executors!=null) {
            executors.clear();
        }else{
            executors = new ArrayList<>();
        }

        if(taskExecutors!=null) {
            for (NewTaskExecutorDTO exequtor : taskExecutors) {
                executors.add(exequtor.createExecutor(exequtor, task, userService, taskService));
            }
            task.setTaskExecutors(executors);
        }

        List<TaskFile> files = task.getTaskFiles();
        if(files!=null) {
            files.clear();
        }else{
            files = new ArrayList<>();
        }

        if(taskFiles!=null) {
            for (TaskFileDTO fileDto : this.taskFiles) {
                files.add(fileDto.createFile(fileDto, task, fileService));
            }
            task.setTaskFiles(files);
        }

        return task;

    }

}
