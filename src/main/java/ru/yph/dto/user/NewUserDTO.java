package ru.yph.dto.user;

import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yph.dto.FieldsValueMatch;
import ru.yph.dto.UniqUserName;
import ru.yph.entities.Division;
import ru.yph.entities.Position;
import ru.yph.entities.user.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@FieldsValueMatch.List(
        @FieldsValueMatch(
                field = "password",
                fieldMatch = "passwordCheck",
                message = "Пароль и проверка пароля не совпадают"
        )
)
public class NewUserDTO {
    @NotBlank(message = "Не указан логин")
    @UniqUserName
    private String login;
    @NotBlank(message = "Не указан email")
    @Email
    private String email;
    private String fullname;
    @NotBlank(message = "Не указано имя пользователя")
    private String shortname;
    private Date birthday;
    private String male;
    private Position position;
    @JsonRawValue
    private Division division;
    private String image;
    private List<RoleDTO> users_roles;
    @JsonRawValue
    private List<NewUserContactDTO> UserContacts;
    @NotBlank(message = "Не указан пароль")
    private String password;
    @NotBlank(message = "Не указано подтверждение пароля")
    private String passwordCheck;
}