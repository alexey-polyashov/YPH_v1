package ru.yph.entities.user;

import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.yph.entities.Division;
import ru.yph.entities.Position;

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
    @JsonRawValue
    private Division division;

    @Column(name = "image")
    private String image;

    @ManyToMany
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "user_role"))
    private Collection<Role> roles;


    @OneToMany
    @JoinTable (name="contacts_owners",
            joinColumns=@JoinColumn (name="user_id"),
            inverseJoinColumns=@JoinColumn(name="contact_id"))
    private Collection<UserContact> userContacts;


    @CreationTimestamp
    @Column(name="created_at")
    private LocalDateTime createTime;

    @UpdateTimestamp
    @Column(name="updated_at")
    private LocalDateTime updateTime;
}