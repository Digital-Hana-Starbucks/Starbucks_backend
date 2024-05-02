package com.hanaro.starbucks.service;

import com.hanaro.starbucks.dto.user.MemberResDto;
import com.hanaro.starbucks.entity.Member;
import com.hanaro.starbucks.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final UserRepository userRepository;

    public List<MemberResDto> getUsers(){
        return userRepository.findAll().stream().map(MemberResDto::new).collect(Collectors.toList());
    }

    public MemberResDto findUser(String userId, String userPw) {
        Optional<Member> optional = userRepository.findByUserIdAndUserPw(userId, userPw);

        return optional.map(MemberResDto::new).orElseGet(MemberResDto::new);
    }
}
