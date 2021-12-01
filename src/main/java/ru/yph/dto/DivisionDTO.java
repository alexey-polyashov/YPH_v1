package ru.yph.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.yph.entities.Division;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class DivisionDTO {
    private Long id;
    private String name;
    private DivisionDTO parrent;
    private int depth_level;
}
