package ru.yph.dto.task;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yph.entities.task.Task;
import ru.yph.entities.task.TaskExecutor;
import ru.yph.entities.user.User;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class TaskExecutorDTO {

    private Long id;
    private Long taskId;
    private Long userId;
    private String userName;
    private Timestamp donetime;
    private String textresult;
    private Long resultId;
    private String resultName;
    private Boolean done;
    private Boolean inprogress;

    public TaskExecutorDTO(TaskExecutor te){
        this.setId(te.getId());
        this.setTaskId(te.getTask().getId());
        this.setUserId(te.getUser().getId());
        this.setUserName(te.getUser().getShortname());
        this.setDonetime(te.getDoneTime());
        this.setTextresult(te.getTextResult());
        if(te.getResult()!=null) {
            this.setResultId(te.getResult().getId());
            this.setResultName(te.getResult().getRepresentation());
        }
        this.setDone(te.getDone());
        this.setInprogress(te.getInProgress());
    }

}
