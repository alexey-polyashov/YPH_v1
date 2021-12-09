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

    public static TaskExecutorDTO createDTO(TaskExecutor te){
        TaskExecutorDTO dto = new TaskExecutorDTO();
        dto.setId(te.getId());
        dto.setTaskId(te.getTask().getId());
        dto.setUserId(te.getUser().getId());
        dto.setUserName(te.getUser().getShortname());
        dto.setDonetime(te.getDonetime());
        dto.setTextresult(te.getTextresult());
        if(te.getResult()!=null) {
            dto.setResultId(te.getResult().getId());
            dto.setResultName(te.getResult().getRepresentation());
        }
        dto.setDone(te.getDone());
        dto.setInprogress(te.getInprogress());
        return dto;
    }

}
