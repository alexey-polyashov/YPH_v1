package ru.yph.dtos.event;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class NewEventParticipantDTO {
    @NotBlank(message = "Не указан участник события")
    private long participantId;
}
