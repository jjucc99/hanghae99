package com.kakao.clone.kakao.Main;

import com.kakao.clone.kakao.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MainResponseDto {
    private Long id;
    private String username;
    private String nickName;
    private String title;
    private String image;
    private String content;

    public MainResponseDto(Long id, String title, String image, String content,
                           User user_temp) {
        this.id = id;
        this.username = user_temp.getUsername();
        this.nickName = user_temp.getNickname();
        this.title = title;
        this.content = content;
        this.image = image;
    }
}

