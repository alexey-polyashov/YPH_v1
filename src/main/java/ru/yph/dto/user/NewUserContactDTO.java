package ru.yph.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class NewUserContactDTO {
    @NotBlank(message="Не указано представление контактной информации")
    private String representation;
    @NotBlank(message="Не указан тип контактной информации")
    private String addressType;
    private String comment;
    @NotBlank(message="Не указан владелец контакта")
    private long contactOwnerId;
}
