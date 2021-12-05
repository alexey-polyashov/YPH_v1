package ru.yph.entities.event;

import lombok.Data;
import ru.yph.entities.user.User;

import javax.persistence.*;

@Entity
@Data
@Table(name = "event_participants")
public class EventParticipant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant", insertable = false, updatable = false)
    private User participant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event", insertable = false, updatable = false)
    private Event event;

}
