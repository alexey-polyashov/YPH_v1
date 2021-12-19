package ru.yph.dtos.task;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yph.entities.task.Task;
import java.sql.Date;
import java.sql.Time;
import java.time.Duration;


@Data
@NoArgsConstructor
public class TaskDTO {

    private Long id;
    private String shortDescribe;
    private String fullDescribe;
    private Boolean repeatable;
    private Duration repeatPeriod;
    private Long authorId;
    private String authorName;
    private Boolean common;
    private Duration durationOfExecute;
    private Date initionDate;
    private Time initionTime;
    private Boolean active;

    public TaskDTO(Task task){
        this.setId(task.getId());
        this.setShortDescribe(task.getShortDescribe());
        this.setFullDescribe(task.getFullDescribe());
        this.setRepeatable(task.getRepeatable());
        this.setRepeatPeriod(task.getRepeatPeriod());
        this.setCommon(task.getCommon());
        if(task.getAuthor()!=null) {
            this.setAuthorId(task.getAuthor().getId());
            this.setAuthorName(task.getAuthor().getShortname());
        }
        this.setInitionDate(task.getInitionDate());
        this.setDurationOfExecute(task.getDurationOfExecute());
        this.setInitionDate(task.getInitionDate());
        this.setInitionTime(task.getInitionTime());
        this.setActive(task.getActive());
    }

}
