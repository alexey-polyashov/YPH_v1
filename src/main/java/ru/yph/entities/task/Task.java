package ru.yph.entities.task;

import com.vladmihalcea.hibernate.type.interval.PostgreSQLIntervalType;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.TypeDef;
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
    @JoinColumn(name = "author")
    private User author;

    @Column(name = "duration_of_execute",
        columnDefinition = "interval")
    private Duration durationOfExecute;

    @Column(name = "inition_date")
    private Date initionDate;

    @Column(name = "inition_time")
    private Time initionTime;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "common")
    private Boolean common;

    @OneToMany(mappedBy = "task")
    private List<TaskFile> taskFiles;

    @OneToMany(mappedBy = "task")
    private List<TaskExecutor> taskExecutors;

    @CreationTimestamp
    @Column(name="created_at")
    private LocalDateTime createTime;

    @UpdateTimestamp
    @Column(name="updated_at")
    private LocalDateTime updateTime;

}
