package com.gamboSupermarket.application.dto;

import lombok.Data;

import javax.persistence.*;

import com.gamboSupermarket.application.model.ERole;

@Data
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;

}