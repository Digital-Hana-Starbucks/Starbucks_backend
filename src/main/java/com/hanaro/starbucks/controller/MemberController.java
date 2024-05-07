package com.hanaro.starbucks.controller;

import com.hanaro.starbucks.config.JwtUtil;
import com.hanaro.starbucks.dto.member.LoginReqDto;
import com.hanaro.starbucks.dto.member.MemberResDto;
import com.hanaro.starbucks.dto.member.MemberUpdateReqDto;
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
    @GetMapping("/{userIdx}")
    public MemberResDto getUser(@PathVariable int userIdx){
        return userService.getUser(userIdx);
    }

    @PostMapping("/login")
    public String login(@RequestParam LoginReqDto user)  {
        MemberResDto findUser = userService.findUser(user.getUserId(), user.getUserPw());

        return jwtUtil.createToken(findUser.getUserId(), Arrays.asList(findUser.getUserRole()));
    }

    @PutMapping("/admin/{userIdx}")
    public void updateUser(@PathVariable int userIdx, @RequestBody MemberUpdateReqDto user){
        userService.updateUser(userIdx, user);
    }

    @DeleteMapping("/admin/{userIdx}")
    public void deleteUser(@PathVariable int userIdx){
        userService.deleteUser(userIdx);
    }

}
