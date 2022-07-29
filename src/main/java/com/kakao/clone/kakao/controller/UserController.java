package com.kakao.clone.kakao.controller;


import com.kakao.clone.kakao.Main.MainloginchecknameDto;
import com.kakao.clone.kakao.dto.LoginIdCheckDto;
import com.kakao.clone.kakao.dto.SignupRequestDto;
import com.kakao.clone.kakao.model.Usertable;
import com.kakao.clone.kakao.security.UserDetailsImpl;
import com.kakao.clone.kakao.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
//@CrossOrigin("http://localhost:3000")
public class UserController {

    private final UserService userService;

    // 회원 가입 요청 처리
    @PostMapping("/api/user/join")
    public String registerUser(@Valid @RequestBody SignupRequestDto requestDto) {
        String res = userService.registerUser(requestDto);
        if (res.equals("")) {
            return "회원가입 성공";
        } else {
            return res;
        }
    }

    @PostMapping("/api/user/username")
    public String userIdCheck(@RequestBody MainloginchecknameDto mainloginchecknameDto) {
        return userService.userIdCheck(mainloginchecknameDto);

    }
        //로그인 유저 정보
    @GetMapping("user/login/auth")
    public Usertable userDetails(@AuthenticationPrincipal UserDetailsImpl userDetails) {
       return userService.userInfo(userDetails);
    }
}