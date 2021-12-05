package ru.yph.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@NoArgsConstructor
@Data
public class NewDivisionDTO {
    private Long id;
    @NotBlank(message="Не указано название подразделения")
    private String name;
    private NewDivisionDTO parrent;
    @NotBlank(message="Не указан уровень подразделения")
    private int depth_level;
    private List<NewDivisionDTO> childs;
}