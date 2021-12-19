package ru.yph.dtos.user;

import lombok.Data;
import lombok.NoArgsConstructor;

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
