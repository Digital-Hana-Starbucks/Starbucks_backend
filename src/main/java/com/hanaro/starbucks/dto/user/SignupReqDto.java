package com.hanaro.starbucks.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignupReqDto {
    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    private String userId;
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String userPw;
    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    private String userNickname;
}
