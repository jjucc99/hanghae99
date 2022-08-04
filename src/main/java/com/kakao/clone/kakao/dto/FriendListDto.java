package com.kakao.clone.kakao.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class FriendListDto {
    private String username;
    private String profileImage;
    private String userStatus;
    private String nickname;

    public FriendListDto (String friendName,String profileImage, String userStatus, String nickname)
    {
        this.nickname = nickname;
        this.username = friendName;
        this.profileImage = profileImage;
        this.userStatus = userStatus;
    }
}
