package com.hanaro.starbucks.service;

import com.hanaro.starbucks.dto.member.MemberResDto;
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

    public MemberResDto getUser(int idx){

        Optional<Member> optionalMember = userRepository.findById(idx);
        if(optionalMember.isEmpty()){
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }
        return new MemberResDto(optionalMember.get());
    }

    public MemberResDto findUser(String userId, String userPw) {
        Optional<Member> optional = userRepository.findByUserIdAndUserPw(userId, userPw);

        return optional.map(MemberResDto::new).orElseGet(MemberResDto::new);
    }

    public void deleteUser(int userIdx){
        userRepository.deleteById(userIdx);
    }
}
