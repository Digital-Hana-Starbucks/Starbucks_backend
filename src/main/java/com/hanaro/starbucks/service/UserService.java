package com.hanaro.starbucks.service;

import com.hanaro.starbucks.dto.user.UserResDto;
import com.hanaro.starbucks.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UserResDto> getUsers(){
        return userRepository.findAll().stream().map(UserResDto::new).collect(Collectors.toList());
    }
}
