package ru.yph.dtos.task;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yph.entities.task.Task;
import ru.yph.entities.task.TaskExecutor;
import ru.yph.entities.user.User;
import ru.yph.exceptions.ResourceNotFoundException;
import ru.yph.services.TaskService;
import ru.yph.services.UserService;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class NewTaskExecutorDTO {

    private Long id;
    private Long taskId;
    @NotNull(message="Не указан исполнитель задачи")
    @Min(value=1, message="Не указан исполнитель задачи")
    private Long userId;

    public TaskExecutor createExecutor(NewTaskExecutorDTO dto, Task task, UserService userService, TaskService taskService){
        TaskExecutor executor = new TaskExecutor();
        executor.setTask(task);
        Long id = dto.getId();
        if(id!=null && id>0){
            executor = taskService.findExecutorById(id).orElseThrow(()->new ResourceNotFoundException("Запись исполнителя с id '" + id + "' не найдена"));
        }
        User newUser = userService.findById(dto.getUserId())
                .orElseThrow(()->new ResourceNotFoundException("Запись исполнителя с id '" + id + "' не найдена"));
        if(executor.getUser()!=newUser) {
            executor.setUser(newUser);
            executor.setDone(false);
            executor.setDoneTime(null);
            executor.setInProgress(false);
            executor.setResult(null);
            executor.setTextResult("");
        }
        return executor;
    }
}
