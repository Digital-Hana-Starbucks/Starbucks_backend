package com.hanaro.starbucks.dto.member;

import com.hanaro.starbucks.enumeration.UserRole;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MemberUpdateReqDto {
    private String userId;
    private String userPw;
    private String userNickname;
    private UserRole userRole;
    private Integer userPoint;
    private LocalDate userJoinDate;
}