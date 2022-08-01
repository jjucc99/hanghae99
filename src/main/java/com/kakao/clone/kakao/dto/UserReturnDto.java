package com.kakao.clone.kakao.dto;

import com.kakao.clone.kakao.model.User;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserReturnDto {
    private User user;

    public UserReturnDto(User user)
    {
        this.user = user;
    }
}
