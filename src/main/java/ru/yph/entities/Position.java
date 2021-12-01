package ru.yph.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "position")
@Entity
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @CreationTimestamp
    @Column(name="created_at")
    private LocalDateTime createTime;

    @UpdateTimestamp
    @Column(name="updated_at")
    private LocalDateTime updateTime;
}