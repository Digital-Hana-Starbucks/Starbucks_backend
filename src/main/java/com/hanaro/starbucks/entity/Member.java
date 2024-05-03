package com.hanaro.starbucks.entity;

import com.hanaro.starbucks.dto.member.MemberUpdateReqDto;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
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

    public void update(MemberUpdateReqDto dto) {
        this.userId = dto.getUserId();
        this.userNickname=dto.getUserNickname();
        this.userPw = dto.getUserPw();
        this.userRole = dto.getUserRole();
        this.userPoint = dto.getUserPoint();
        this.userJoinDate = LocalDate.now();
    }
}