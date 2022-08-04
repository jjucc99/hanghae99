package com.kakao.clone.kakao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDto {
    private String roomId;
    private String writer;
    private String message;
}
