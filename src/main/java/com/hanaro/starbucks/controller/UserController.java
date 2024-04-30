package com.hanaro.starbucks.controller;

import com.hanaro.starbucks.dto.user.UserResDto;
import com.hanaro.starbucks.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping("")
    public List<UserResDto> getUsers(){
        return userService.getUsers();
    }
}
