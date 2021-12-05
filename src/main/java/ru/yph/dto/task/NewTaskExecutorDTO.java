package ru.yph.dto.task;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yph.entities.user.User;

@Data
@NoArgsConstructor
public class NewTaskExecutorDTO {
    private Long id;
    private Long taskId;
    private User userId;
}
