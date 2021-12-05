package ru.yph.dto.event;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EventParticipantDTO {
    private long id;
    private long participantId;
    private String participantName;
}
