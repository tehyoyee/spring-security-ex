package com.tete.back.dto.enums;

import lombok.Getter;

import java.util.List;

@Getter
public enum Role {

    ADMIN(List.of(Authority.READ, Authority.WRITE)),
    GENERAL(List.of(Authority.READ));

    private final List<Authority> authorities;

    Role(List<Authority> authorities) {
        this.authorities = authorities;
    }


}
