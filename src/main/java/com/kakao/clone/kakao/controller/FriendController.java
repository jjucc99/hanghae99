package com.kakao.clone.kakao.controller;


import com.kakao.clone.kakao.dto.FriendNewRequertDto;
import com.kakao.clone.kakao.dto.FriendResponseDto;
import com.kakao.clone.kakao.dto.UserReturnDto;
import com.kakao.clone.kakao.model.User;
import com.kakao.clone.kakao.security.UserDetailsImpl;
import com.kakao.clone.kakao.service.FriendService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FriendController {

    private final FriendService FriendService;

    public FriendController(FriendService FriendService) {
        this.FriendService = FriendService;
    }

    @GetMapping("/api/friend/list") //메인 투척.
    public FriendResponseDto showFriendList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return FriendService.showFriendList(userDetails);
    }


    @PostMapping("/api/friend/new")
    public UserReturnDto friendNew(@RequestBody FriendNewRequertDto friendNewRequertDto,
                                   @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        return FriendService.friendNew(friendNewRequertDto,userDetails);
    }

    @GetMapping("/")
    public String gooja(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userDetails.getUser().getNickname();
    }
}
