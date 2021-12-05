package ru.yph.dto.task;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yph.entities.task.TaskFile;
import ru.yph.entities.user.User;
import java.sql.Date;
import java.sql.Time;
import java.time.Duration;
import java.util.List;

@Data
@NoArgsConstructor
public class TaskDTO {
    private Long id;
    private String short_describe;
    private String full_describe;
    private Date date;
    private Time time;
    private boolean repeatable;
    private boolean repeat_period;
    private User author;
    private String authorName;
    private User owner;
    private String ownerName;
    private Duration duration_of_execute;
    private Date inition_date;
    private Time inition_time;
    private boolean active;
    private List<TaskFile> taskfiles;
    private List<TaskExecutorDTO> taskExecutors;
}
