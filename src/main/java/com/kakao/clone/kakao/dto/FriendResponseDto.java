package com.kakao.clone.kakao.dto;

import lombok.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class FriendResponseDto {
    private FriendListDto userProfile;
    private List<FriendListDto> friendList;

    public FriendResponseDto(FriendListDto userProfile,
                            List<FriendListDto> friendList){
        this.userProfile =userProfile;
        this.friendList = friendList;
    }
}
