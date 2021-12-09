package ru.yph.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class UserContactDTO {
    private Long id;
    private String representation;
    private Long addressType;
    private String addressTypeName;
    private String comment;
    private Long contactOwnerId;
}
