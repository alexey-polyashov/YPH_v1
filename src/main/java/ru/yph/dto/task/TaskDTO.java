package ru.yph.dto.task;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yph.entities.task.Task;
import ru.yph.entities.task.TaskExecutor;
import ru.yph.entities.task.TaskFile;

import java.sql.Date;
import java.sql.Time;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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
    private Long ownerId;
    private String ownerName;
    private Duration durationOfExecute;
    private Date initionDate;
    private Time initionTime;
    private Boolean active;
    private List<TaskFileDTO> taskFiles = new ArrayList<>();
    private List<TaskExecutorDTO> taskExecutors = new ArrayList<>();

    public static TaskDTO createDTO(Task task){

        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(task.getId());
        taskDTO.setShortDescribe(task.getShortDescribe());
        taskDTO.setFullDescribe(task.getFullDescribe());
        taskDTO.setRepeatable(task.getRepeatable());
        taskDTO.setRepeatPeriod(task.getRepeatPeriod());
        if(task.getAuthor()!=null) {
            taskDTO.setAuthorId(task.getAuthor().getId());
            taskDTO.setAuthorName(task.getAuthor().getShortname());
        }
        taskDTO.setInitionDate(task.getInitionDate());
        if(task.getOwner()!=null) {
            taskDTO.setOwnerId(task.getOwner().getId());
            taskDTO.setOwnerName(task.getOwner().getShortname());
        }
        taskDTO.setDurationOfExecute(task.getDurationOfExecute());
        taskDTO.setInitionDate(task.getInitionDate());
        taskDTO.setInitionTime(task.getInitionTime());
        taskDTO.setActive(task.getActive());

        for(TaskExecutor exequtor: task.getTaskExecutors()){
            taskDTO.taskExecutors.add(TaskExecutorDTO.createDTO(exequtor));
        }
        for(TaskFile file: task.getTaskFiles()){
            taskDTO.taskFiles.add(TaskFileDTO.createDTO(file));
        }

        return taskDTO;
    }

}
