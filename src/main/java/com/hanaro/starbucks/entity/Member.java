package com.hanaro.starbucks.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_idx")
    private int userIdx;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_pw")
    private String userPw;

    @Column(name = "user_nickname")
    private String userNickname;

    @Column(name = "user_role")
    private String userRole;

    @Column(name = "user_point")
    private int userPoint;

    @Column(name = "user_join_date")
    private LocalDate userJoinDate;

    @Builder
    public Member(String userId, String userPw, String userNickname) {
        this.userId = userId;
        this.userPw = userPw;
        this.userNickname = userNickname;
        this.userRole = "USER";
        this.userPoint = 0;
        this.userJoinDate = LocalDate.now();
    }
}