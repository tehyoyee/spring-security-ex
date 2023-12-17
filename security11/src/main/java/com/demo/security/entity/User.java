package com.demo.security.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Data
@RequiredArgsConstructor
@Entity
public class User {

    @Id
    private String username;

    @Column
    private String password;

}