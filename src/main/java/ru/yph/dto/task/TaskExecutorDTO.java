package ru.yph.dto.task;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yph.entities.task.Task;
import ru.yph.entities.user.User;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class TaskExecutorDTO {
    private Long id;
    private Task task;
    private User userId;
    private String userName;
    private Timestamp donetime;
    private String textresult;
    private String result;
    private boolean done;
    private boolean inprogress;

}
