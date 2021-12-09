package ru.yph.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import ru.yph.dto.FieldsValueMatch;
import ru.yph.dto.UniqUserEmail;
import ru.yph.dto.UniqUserName;

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
public class NewUserDTOWithContacts {
    @NotBlank(message = "Не указан логин")
    @UniqUserName
    private String login;
    @NotBlank(message = "Не указан email")
    @Email
    @UniqUserEmail
    private String email;
    private String fullname;
    @NotBlank(message = "Не указано имя пользователя")
    private String shortname;
    private Date birthday;
    private String male;
    @Id
    private Long position;
    @Id
    private Long division;
    private String image;
    private List<RoleDTO> users_roles;
    private List<NewUserContactDTO> UserContacts;
    @NotBlank(message = "Не указан пароль")
    private String password;
    @NotBlank(message = "Не указано подтверждение пароля")
    private String passwordCheck;
}