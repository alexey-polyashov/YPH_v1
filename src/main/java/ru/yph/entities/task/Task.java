package ru.yph.entities.task;

import com.vladmihalcea.hibernate.type.interval.PostgreSQLIntervalType;
import lombok.Data;
import org.hibernate.annotations.TypeDef;
import ru.yph.entities.user.User;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.time.Duration;
import java.util.List;

@Entity
@Data
@Table(name = "tasks")
@TypeDef(
        typeClass = PostgreSQLIntervalType.class,
        defaultForType = Duration.class
)
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "shortdescribe")
    private String shortDescribe;

    @Column(name = "fulldescribe")
    private String fullDescribe;

    @Column(name = "repeatable")
    private Boolean repeatable;

    @Column(name = "repeat_period",
        columnDefinition = "interval")
    private Duration repeatPeriod;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author", insertable = false, updatable = false)
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner", insertable = false, updatable = false)
    private User owner;

    @Column(name = "duration_of_execute",
        columnDefinition = "interval")
    private Duration durationOfExecute;

    @Column(name = "inition_date")
    private Date initionDate;

    @Column(name = "inition_time")
    private Time initionTime;

    @Column(name = "active")
    private Boolean active;

    @OneToMany(mappedBy = "task")
    private List<TaskFile> taskFiles;

    @OneToMany(mappedBy = "task")
    private List<TaskExecutor> taskExecutors;

}
