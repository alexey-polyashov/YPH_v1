package ru.yph.dtos.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class NewUserContactDTO {
    @NotBlank(message="Не указано представление контактной информации")
    private String representation;
    @NotBlank(message="Не указан тип контактной информации")
    private Long addressType;
    private String comment;
    @NotBlank(message="Не указан владелец контакта")
    @Id
    private Long contactOwnerId;
}
