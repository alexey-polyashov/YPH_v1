package ru.yph.dto.task;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yph.entities.task.Task;
import ru.yph.entities.task.TaskExecutor;
import ru.yph.entities.task.TaskFile;

import java.sql.Date;
import java.sql.Time;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class FullTaskDTO {

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
    private List<TaskFileDTO> taskFiles = new ArrayList<>();
    private List<TaskExecutorDTO> taskExecutors = new ArrayList<>();

    public FullTaskDTO (Task task){

        this.setId(task.getId());
        this.setShortDescribe(task.getShortDescribe());
        this.setFullDescribe(task.getFullDescribe());
        this.setRepeatable(task.getRepeatable());
        this.setRepeatPeriod(task.getRepeatPeriod());
        if(task.getAuthor()!=null) {
            this.setAuthorId(task.getAuthor().getId());
            this.setAuthorName(task.getAuthor().getShortname());
        }
        this.setInitionDate(task.getInitionDate());
        this.setCommon(task.getCommon());
        this.setDurationOfExecute(task.getDurationOfExecute());
        this.setInitionDate(task.getInitionDate());
        this.setInitionTime(task.getInitionTime());
        this.setActive(task.getActive());

        for(TaskExecutor exequtor: task.getTaskExecutors()){
            this.taskExecutors.add(new TaskExecutorDTO(exequtor));
        }
        for(TaskFile file: task.getTaskFiles()){
            this.taskFiles.add(new TaskFileDTO(file));
        }

    }

}
