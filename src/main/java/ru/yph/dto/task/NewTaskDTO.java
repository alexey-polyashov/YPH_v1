package ru.yph.dto.task;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yph.entities.task.TaskFile;

import javax.validation.constraints.NotBlank;
import java.sql.Date;
import java.sql.Time;
import java.time.Duration;
import java.util.List;

@Data
@NoArgsConstructor
public class NewTaskDTO {
    @NotBlank(message="Не указано краткое описание задачи")
    private String short_describe;
    private String full_describe;
    @NotBlank(message="Не указана дата выполнения задачи")
    private Date date;
    @NotBlank(message="Не указано время выполнения задачи")
    private Time time;
    private boolean repeatable;
    private boolean repeat_period;
    @NotBlank(message="Не указан автор задачи")
    private long authorId;
    private long ownerId;
    private Duration duration_of_execute;
    @NotBlank(message="Не указана дата постановки задачи")
    private Date inition_date;
    @NotBlank(message="Не указано время постановки задачи")
    private Time inition_time;
    private boolean active;
    private List<TaskFile> taskFiles;
    private List<TaskExecutorDTO> taskExecutors;
}
