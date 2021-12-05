package ru.yph.entities.task;

import lombok.Data;
import ru.yph.entities.user.User;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.time.Duration;
import java.util.List;

@Entity
@Data
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "shortdescribe")
    private String short_describe;

    @Column(name = "fulldescribe")
    private String full_describe;

    @Column(name = "date")
    private Date date;

    @Column(name = "time")
    private Time time;

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

    @Column(name = "inition_date")
    private Date inition_date;

    @Column(name = "inition_time")
    private Time inition_time;

    @Column(name = "active")
    private boolean active;

    @OneToMany(mappedBy = "task")
    private List<TaskFile> taskFiles;

    @OneToMany(mappedBy = "task")
    private List<TaskExecutor> taskExecutors;

}
