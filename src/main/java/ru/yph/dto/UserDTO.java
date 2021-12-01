package ru.yph.dto;

import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.yph.entities.Division;
import ru.yph.entities.Position;
import ru.yph.entities.Role;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String login;
    private String email;
    private String fullname;
    private String shortname;
    private Date birthday;
    private String male;
    private Position position;
    @JsonRawValue
    private Division division;
    private String image;
    private Collection<Role> roles;
}