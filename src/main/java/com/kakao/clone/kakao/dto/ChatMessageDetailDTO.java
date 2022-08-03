package com.kakao.clone.kakao.dto;

import com.kakao.clone.kakao.model.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDetailDTO {
    private String roomId;
    private String writer;
    private String message;
    public static ChatMessageDetailDTO toChatMessageDetailDTO(ChatMessage chatMessage){
        ChatMessageDetailDTO chatMessageDetailDTO = new ChatMessageDetailDTO();

        chatMessageDetailDTO.setRoomId(chatMessage.getChatRoom().getRoomId());

        chatMessageDetailDTO.setWriter(chatMessage.getWriter());
        chatMessageDetailDTO.setMessage(chatMessage.getMessage());

        return chatMessageDetailDTO;

    }
}