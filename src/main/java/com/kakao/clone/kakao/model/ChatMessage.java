package com.kakao.clone.kakao.model;

import com.kakao.clone.kakao.dto.ChatMessageSaveDTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatRoom_id")
    private ChatRoom chatRoom;

    private String writer;

    @Column
    private String message;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime sendDate;

    public static ChatMessage toChatEntity(ChatMessageSaveDTO chatMessageSaveDTO, ChatRoom chatRoomEntity){
        ChatMessage chatMessageEntity = new ChatMessage();
        chatMessageEntity.setChatRoom(chatRoomEntity);
        chatMessageEntity.setWriter(chatMessageSaveDTO.getWriter());
        chatMessageEntity.setMessage(chatMessageSaveDTO.getMessage());
        return chatMessageEntity;
    }
}
