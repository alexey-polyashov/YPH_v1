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
    @JoinColumn(name = "task")
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "donetime")
    private Timestamp doneTime;

    @Column(name = "textresult")
    private String textResult;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "result")
    private TaskResult result;

    @Column(name = "done")
    private Boolean done;

    @Column(name = "inprogress")
    private Boolean inProgress;

}
