package ru.yph.entities.user;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "grants")
public class RoleGrant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;


}