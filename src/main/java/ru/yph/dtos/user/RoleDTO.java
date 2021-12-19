package ru.yph.dtos.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class RoleDTO {
    @NotBlank(message = "Не указано имя роли")
    private String name;
}
