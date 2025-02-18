package com.taehyeong.backend.authentication.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(value = EnumType.STRING)
    private AuthorityType name;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

}
