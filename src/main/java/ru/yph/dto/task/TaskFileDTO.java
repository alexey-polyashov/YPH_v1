package ru.yph.dto.task;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yph.entities.task.TaskExecutor;
import ru.yph.entities.task.TaskFile;

@Data
@NoArgsConstructor
public class TaskFileDTO {
    String representation;
    String path;

    public static TaskFileDTO createDTO(TaskFile model){
        TaskFileDTO dto = new TaskFileDTO();
        dto.setRepresentation(model.getRepresentation());
        dto.setPath(model.getPath());
        return dto;
    }

}
