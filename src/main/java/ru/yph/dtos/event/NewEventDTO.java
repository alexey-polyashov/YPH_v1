package ru.yph.dtos.event;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;
import java.sql.Date;
import java.sql.Time;
import java.time.Duration;
import java.util.List;

@Data
@NoArgsConstructor
public class NewEventDTO {
    @NotBlank(message="Не указано краткое описание события")
    private String short_describe;
    private String full_describe;
    @NotBlank(message="Не указана дата события")
    private Date date;
    @NotBlank(message="Не указано время события")
    private Time time;
    private boolean repeatable;
    private boolean repeat_period;
    @NotBlank(message="Не указан автор события")
    @Id
    private long authorId;
    @Id
    private long ownerId;
    private Duration duration_of_execute;
    private List<NewEventParticipantDTO> eventParticipants;
}
