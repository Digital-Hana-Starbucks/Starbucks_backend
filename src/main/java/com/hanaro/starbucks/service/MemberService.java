package com.hanaro.starbucks.service;

import com.hanaro.starbucks.dto.member.MemberResDto;
import com.hanaro.starbucks.dto.member.SignupReqDto;
import com.hanaro.starbucks.entity.Member;
import com.hanaro.starbucks.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public List<MemberResDto> getUsers(){
        return memberRepository.findAll().stream().map(MemberResDto::new).collect(Collectors.toList());
    }

    @Transactional
    public boolean findUserByUserId(String userId) {
        return memberRepository.existsByUserId(userId);
    }

    @Transactional
    public MemberResDto createUser(SignupReqDto user) {
        Member saveEntity = Member.builder()
                .userId(user.getUserId())
                .userPw(bCryptPasswordEncoder.encode(user.getUserPw()))
                .userNickname(user.getUserNickname())
                .build();
        Member newEntity = memberRepository.save(saveEntity);
        return MemberResDto.builder()
                .user(newEntity)
                .build();
    }

    @Transactional
    public MemberResDto findUserByUserIdAndUserPw(String userId, String userPw) {
        Member findUser = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new BadCredentialsException("아이디에 맞는 회원 정보를 찾을 수 없습니다."));

        if (bCryptPasswordEncoder.matches(userPw, findUser.getUserPw())) {
            return MemberResDto.builder()
                    .user(findUser)
                    .build();
        } else {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }
    }

}
