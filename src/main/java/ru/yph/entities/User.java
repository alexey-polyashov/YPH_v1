package ru.yph.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "login")
    private String login;

    @Column(name = "token")
    private String token;

    @Column(name = "email")
    private String email;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "shortname")
    private String shortname;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "male")
    private String male;

    @ManyToOne
    @JoinColumn(name = "position")
    private Position position;

    @ManyToOne
    @JoinColumn(name = "division")
    private Division division;

    @Column(name = "image")
    private String image;

    @CreationTimestamp
    @Column(name="created_at")
    private LocalDateTime createTime;

    @UpdateTimestamp
    @Column(name="updated_at")
    private LocalDateTime updateTime;

    @ManyToMany
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;
}