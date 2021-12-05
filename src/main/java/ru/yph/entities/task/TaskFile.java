package ru.yph.entities.task;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "task_files")
public class TaskFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne()
    @JoinColumn(name="task", insertable = false, updatable = false)
    private Task task;

    @Column(name = "representation")
    private String representation;

    @Column(name = "path")
    private String path;


}
