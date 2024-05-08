package com.hanaro.starbucks.controller;

import com.hanaro.starbucks.config.JwtUtil;
import com.hanaro.starbucks.dto.member.*;
import com.hanaro.starbucks.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.hanaro.starbucks.util.APIConstant.API_VERSION;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_VERSION + "/users")
public class MemberController {
    private final MemberService memberService;
    private final JwtUtil jwtUtil;

    @GetMapping("/admin")
    public List<MemberResDto> getUsers(){
        return memberService.getUsers();
    }
    @GetMapping("/admin/{userIdx}")
    public MemberResDto getUser(@PathVariable int userIdx){
        return memberService.getUser(userIdx);
    }

    @PostMapping("/signup")
    @ResponseBody
    public ResponseEntity<?> signup(@RequestBody SignupReqDto user) {
        boolean findUser = memberService.findUserByUserId(user.getUserId());
        if (!findUser) {
            MemberResDto newUser = memberService.createUser(user);
            return ResponseEntity.ok(newUser.getUserId());
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 사용자입니다.");
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<?> login(@RequestBody LoginReqDto user)  {
        MemberResDto findUser = memberService.findUserByUserIdAndUserPw(user.getUserId(), user.getUserPw());
        System.out.println(findUser);
        if (findUser != null) {
            String token = jwtUtil.createToken(findUser.getUserId(), Arrays.asList(findUser.getUserRole()));
            Map<String, Object> response = new HashMap<>();
            response.put("user", findUser); // 사용자 정보
            response.put("token", token);   // 토큰
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인에 실패하였습니다. 아이디와 비밀번호를 확인해주세요.");
        }
    }

    @PutMapping("/admin/{userIdx}")
    public void updateUser(@PathVariable int userIdx, @RequestBody MemberUpdateReqDto user){
        memberService.updateUser(userIdx, user);
    }

    @DeleteMapping("/admin/{userIdx}")
    public void deleteUser(@PathVariable int userIdx){
        memberService.deleteUser(userIdx);
    }

    @GetMapping("/points")
    public PointResDto getUserPoint(@RequestHeader MultiValueMap<String, String> headers){
        String originalToken = headers.getFirst("authorization");
        return memberService.getUserPoint(originalToken.substring(7, originalToken.length()));
    }
}
