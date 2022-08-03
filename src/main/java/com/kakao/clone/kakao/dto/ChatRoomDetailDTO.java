package com.kakao.clone.kakao.dto;


import com.kakao.clone.kakao.model.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomDetailDTO {
    private String participants;
    private String roomId;
    private String name;

    public static ChatRoomDetailDTO toChatRoomDetailDTO(ChatRoom ChatRoom){
        ChatRoomDetailDTO chatRoomDetailDTO = new ChatRoomDetailDTO();
        chatRoomDetailDTO.setParticipants(ChatRoom.getParticipants());
        chatRoomDetailDTO.setRoomId(ChatRoom.getRoomId());
        chatRoomDetailDTO.setName(ChatRoom.getRoomName());
        return chatRoomDetailDTO;
    }
}
