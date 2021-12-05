package ru.yph.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class UserContactDTO {
    private Long id;
    private String representation;
    private String addressType;
    private String comment;
    private long contactOwnerId;
}
