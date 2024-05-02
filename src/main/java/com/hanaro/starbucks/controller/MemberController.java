package com.hanaro.starbucks.controller;

import com.hanaro.starbucks.config.JwtUtil;
import com.hanaro.starbucks.dto.user.LoginReqDto;
import com.hanaro.starbucks.dto.user.MemberResDto;
import com.hanaro.starbucks.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class MemberController {
    private final MemberService userService;
    private final JwtUtil jwtUtil;

    @GetMapping("")
    public List<MemberResDto> getUsers(){
        return userService.getUsers();
    }

    @PostMapping("/login")
    public String login(@RequestParam LoginReqDto user)  {
        MemberResDto findUser = userService.findUser(user.getUserId(), user.getUserPw());

        return jwtUtil.createToken(findUser.getUserId(), Arrays.asList(findUser.getUserRole()));
    }

}
