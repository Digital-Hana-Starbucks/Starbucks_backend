package com.hanaro.starbucks.service.impl;

import com.hanaro.starbucks.entity.Member;
import com.hanaro.starbucks.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public Member loadUserByUsername(String userId) throws UsernameNotFoundException {
        Optional<Member> optional = Optional.ofNullable(memberRepository.findByUserId(userId).orElseThrow(() -> new BadCredentialsException("아이디에 맞는 회원 정보를 찾을 수 없습니다.")));
        return optional.get();
    }
}