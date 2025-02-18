package com.taehyeong.backend.authentication.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

//    @ElementCollection(fetch = FetchType.EAGER)
//    @CollectionTable(name = "authority", joinColumns = @JoinColumn(name = "role_id"))
//    @Enumerated(EnumType.STRING)
//    private Set<Authority> authorityList = new HashSet<>();

    @Builder
    public Role(String name) {
        this.name = name;
    }

}

//CREATE TABLE role (
//        id BIGINT NOT NULL AUTO_INCREMENT,
//        name VARCHAR(255),
//PRIMARY KEY (id)
//) ENGINE=InnoDB;
