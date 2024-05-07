package com.hanaro.starbucks.dto.member;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MemberUpdateReqDto {
    private String userId;
    private String userPw;
    private String userNickname;
    private String userRole;
    private Integer userPoint;
    private LocalDate userJoinDate;
}