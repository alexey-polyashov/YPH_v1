package ru.yph.entities;

import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "divisions")
@Data
@NoArgsConstructor
@Entity
public class Division {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="parrent", nullable=false)
    @JsonRawValue
    private Division parrent;

    @Column(name = "depth_level")
    private int depth_level;

    @CreationTimestamp
    @Column(name="created_at")
    private LocalDateTime createTime;

    @UpdateTimestamp
    @Column(name="updated_at")
    private LocalDateTime updateTime;

}