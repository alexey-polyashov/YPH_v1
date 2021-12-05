package ru.yph.entities.task;

import lombok.Data;
import ru.yph.entities.user.User;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "task_executors")
public class TaskExecutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task", insertable = false, updatable = false)
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user", insertable = false, updatable = false)
    private User user;
    private String userName;

    @Column(name = "donetime")
    private Timestamp donetime;

    @Column(name = "textresult")
    private String textresult;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "result", insertable = false, updatable = false)
    private TaskResult result;

    @Column(name = "done")
    private boolean done;

    @Column(name = "inprogress")
    private boolean inprogress;

}
