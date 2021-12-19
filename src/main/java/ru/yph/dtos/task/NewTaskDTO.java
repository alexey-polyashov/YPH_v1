package ru.yph.dtos.task;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yph.dtos.DateValidator;
import ru.yph.dtos.DurationValidator;
import ru.yph.dtos.TimeValidator;
import ru.yph.entities.task.Task;
import ru.yph.entities.user.User;
import ru.yph.exceptions.NotValidFields;
import ru.yph.exceptions.ResourceNotFoundException;
import ru.yph.exceptions.Violation;
import ru.yph.services.TaskService;
import ru.yph.services.UserService;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;

@Data
@NoArgsConstructor
@Slf4j
public class NewTaskDTO {

    private Long id;

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
    private Boolean active;
    private Boolean common;

    public Task createTask(UserService userService, TaskService taskService) throws ParseException {

        Task task = new Task();

        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormater = new SimpleDateFormat("HH:MM");

        Long id = this.getId();
        if(id!=null && id!=0){
            task = taskService.findById(id).orElseThrow(()->new ResourceNotFoundException("Задача с id '" + id + "' не найдена!"));
        }

        task.setShortDescribe(this.getShortDescribe());
        task.setFullDescribe(this.getFullDescribe());
        Long authorId = this.getAuthorId();
        if(authorId!=null && authorId>0) {
            log.info("authorId - " + authorId);
            User author = userService.findById(authorId).orElseThrow(()->new ResourceNotFoundException("Пользователь с id '" + authorId + "' не найдена!"));
            task.setAuthor(author);
            log.info("task.author - " + task.getAuthor().getShortname());
        }

        Date dateVal = new Date(dateFormater.parse(this.getInitionDate()).getTime());
        Time timeVal = new Time(timeFormater.parse(this.getInitionTime()).getTime());
        task.setInitionDate(dateVal);
        task.setInitionTime(timeVal);
        task.setRepeatable(this.getRepeatable());
        if(this.repeatable){
            try {
                Duration d = Duration.parse(this.getRepeatPeriod());
                task.setRepeatPeriod(d);
            }catch(DateTimeParseException e) {
                throw new NotValidFields(new ArrayList<>(Arrays.asList(new Violation("repeatPeriod","Не верный формат периода"))));
            }
        }
        task.setCommon(this.getCommon());
        task.setDurationOfExecute(Duration.parse(this.getDurationOfExecute()));
        task.setActive(this.getActive());

        return task;

    }

}
