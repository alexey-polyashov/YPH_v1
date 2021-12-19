package ru.yph.dtos.task;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yph.entities.task.Task;
import ru.yph.entities.task.TaskFile;
import ru.yph.exceptions.ResourceNotFoundException;
import ru.yph.services.TaskFileService;

@Data
@NoArgsConstructor
public class TaskFileDTO {

    Long id;
    String representation;
    String path;

    public TaskFileDTO(TaskFile model){
        this.setRepresentation(model.getRepresentation());
        this.setPath(model.getPath());
    }

    public TaskFile createFile(TaskFileDTO dto, Task task, TaskFileService filesService){
        TaskFile file = new TaskFile();
        Long id = dto.getId();
        if(id!=null && id!=0){
            file = filesService.findById(id)
                    .orElseThrow(()->new ResourceNotFoundException("Pfgbcm afqkf c id '" + id + "' не найдена"));
        }
        file.setTask(task);
        file.setRepresentation(dto.getRepresentation());
        file.setPath(dto.getPath());
        return file;
    }

}
