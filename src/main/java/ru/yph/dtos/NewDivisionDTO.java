package ru.yph.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Data
public class NewDivisionDTO {
    private Long id;
    @NotBlank(message="Не указано название подразделения")
    private String name;
    private Long parrent;
    @NotBlank(message="Не указан уровень подразделения")
    private int depthLevel;
}
