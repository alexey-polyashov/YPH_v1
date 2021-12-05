package ru.yph.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.yph.entities.user.RoleGrant;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
public class RoleDTO {
    @NotBlank(message = "Не указано имя роли")
    private String name;
}
