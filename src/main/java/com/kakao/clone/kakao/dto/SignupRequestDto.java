package com.kakao.clone.kakao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestDto {
    private String username;
    private String realname;
    private String nickname;
    private String password;
    private String checkPassword;
    private String profileImage;
    private String encodeUserName;
    private Boolean checkUsername;
}