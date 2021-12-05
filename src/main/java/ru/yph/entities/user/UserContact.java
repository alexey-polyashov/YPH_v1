package ru.yph.entities.user;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "contacts")
public class UserContact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="representation")
    private String representation;

    @Column(name="address_type")
    private String addressType;

    @Column(name="comment")
    private String comment;

    @CreationTimestamp
    @Column(name="created_at")
    private LocalDateTime createTime;

    @UpdateTimestamp
    @Column(name="updated_at")
    private LocalDateTime updateTime;

    @ManyToOne
    @JoinTable (name="contacts_owners",
            joinColumns=@JoinColumn (name="contact_id"),
            inverseJoinColumns=@JoinColumn(name="user_id"))
    private User user;

}
