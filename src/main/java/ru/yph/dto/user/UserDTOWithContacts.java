package ru.yph.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yph.dto.DivisionDTO;
import ru.yph.dto.PositionDTO;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class UserDTOWithContacts {
    private Long id;
    private String login;
    private String email;
    private String fullname;
    private String shortname;
    private Date birthday;
    private String male;
    private PositionDTO position;
    private DivisionDTO division;
    private String image;
    private List<RoleDTO> roles = new ArrayList<>();
    private List<UserContactDTO> UserContacts = new ArrayList<>();
}
