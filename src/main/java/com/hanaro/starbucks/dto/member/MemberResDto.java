package com.hanaro.starbucks.dto.member;

import com.hanaro.starbucks.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class MemberResDto {
    private int userIdx;

    private String userId;

    private String userPw;

    private String userNickname;

    private String userRole;

    private int userPoint;

    private LocalDate userJoinDate;

    public MemberResDto(Member user){
        this.userIdx = user.getUserIdx();
        this.userId = user.getUserId();
        this.userPw = user.getUserPw();
        this.userNickname = user.getUserNickname();
        this.userRole = user.getUserRole();
        this.userPoint = user.getUserPoint();
        this.userJoinDate = user.getUserJoinDate();
    }
}
