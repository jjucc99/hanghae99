package com.kakao.clone.kakao.dto;

import com.kakao.clone.kakao.model.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FriendListDto {
    private String username;
    private String profileImage;
    private String userStatus;
    private String nickname;

    public FriendListDto (User user)
    {
        this.nickname = user.getNickname();
        this.username = user.getUsername();
        this.profileImage = user.getProfileImage();
        this.userStatus = user.getUserStatus();
    }
}
