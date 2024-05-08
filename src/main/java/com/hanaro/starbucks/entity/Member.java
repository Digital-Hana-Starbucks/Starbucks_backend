package com.hanaro.starbucks.entity;

import com.hanaro.starbucks.dto.member.MemberUpdateReqDto;
import com.hanaro.starbucks.enumeration.UserRole;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user")
public class Member implements UserDetails {

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
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Column(name = "user_point")
    private int userPoint;

    @Column(name = "user_join_date")
    private LocalDate userJoinDate;

    @Builder
    public Member(String userId, String userPw, String userNickname) {
        this.userId = userId;
        this.userPw = userPw;
        this.userNickname = userNickname;
        this.userRole = UserRole.USER;
        this.userPoint = 0;
    }

    public void update(MemberUpdateReqDto dto) {
        this.userId = dto.getUserId();
        this.userNickname=dto.getUserNickname();
        this.userPw = dto.getUserPw();
        this.userRole = dto.getUserRole();
        this.userPoint = dto.getUserPoint();
        this.userJoinDate = LocalDate.now();
    }

    public void update(int userPoint) {
        this.userPoint = userPoint;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //계정의 권한 목록을 리턴함.
        Set<GrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority(userRole.getValue()));
        return roles;
    }

    @Override
    public String getUsername() {
        return this.userId; //계정의 고유한 아이디 리턴
    }

    @Override
    public String getPassword() {
        return this.userPw; //계정의 암호 리턴
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; //계정의 만료 여부 리턴 - true는 사용가능하다는 의미
    }
    @Override
    public boolean isAccountNonLocked() {
        return true; //계정의 잠김 여부 리턴
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return  true; //비밀번호 만료 여부 리턴
    }
    @Override
    public boolean isEnabled() {
        return true; //계정의 활성화 여부 리턴
    }
}