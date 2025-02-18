package com.taehyeong.backend.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Setter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

//    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER)
//    private final List<Authority> authorities = new ArrayList<>();

    @Builder
    public Member(String username, String password) {

        this.username = username;
        this.password = password;

    }


}
