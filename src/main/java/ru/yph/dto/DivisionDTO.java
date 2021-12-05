package ru.yph.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@NoArgsConstructor
@Data
public class DivisionDTO {
    private Long id;
    private String name;
    private int depth_level;
}
