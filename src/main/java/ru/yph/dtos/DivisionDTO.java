package ru.yph.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class DivisionDTO {
    private Long id;
    private String name;
    private int depthLevel;
}
