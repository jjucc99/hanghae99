package com.kakao.clone.kakao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomDTO {

    private String participants;
    private String roomId;
    private String name;

    private Set<WebSocketSession> sessions = new HashSet<>();

    //WebSocketSession 은 Spring 에서 Websocket Connection 이 맺어진 세션
    public ChatRoomDTO(String roomId, String participants, String name) {
        this.roomId = roomId;
        this.participants = participants;
        this.name = name;
    }
}
