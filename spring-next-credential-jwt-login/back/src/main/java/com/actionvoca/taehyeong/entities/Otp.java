package com.actionvoca.taehyeong.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Otp {

    @Id
    @Pattern(regexp = "^[a-zA-Z0-9]+@[0-9a-zA-Z]+\\.[a-z]+$", message = "올바른 이메일을 입력해주세요.")
    private String email;

    @Column
    private String code;

    private LocalDateTime cre;

    private LocalDateTime exp;

    private boolean verified;

}
