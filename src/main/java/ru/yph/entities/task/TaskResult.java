package ru.yph.entities.task;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="resultsref")
public class TaskResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column
    private String representation;

}
