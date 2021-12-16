package ru.yph.entities.event;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.yph.entities.user.User;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "shortdescribe")
    private String short_describe;

    @Column(name = "fulldescribe")
    private String full_describe;

    @Column(name = "inition_date")
    private Date initionDate;

    @Column(name = "inition_time")
    private Time initionTime;

    @Column(name = "repeatable")
    private boolean repeatable;

    @Column(name = "repeat_period")
    private boolean repeat_period;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author", insertable = false, updatable = false)
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner", insertable = false, updatable = false)
    private User owner;

    @Column(name = "duration_of_execute")
    private Duration duration_of_execute;

    @OneToMany(mappedBy = "event")
    private List<EventParticipant> eventParticipants;

    @CreationTimestamp
    @Column(name="created_at")
    private LocalDateTime createTime;

    @UpdateTimestamp
    @Column(name="updated_at")
    private LocalDateTime updateTime;

}
