package com.kakao.clone.kakao.model;



import com.kakao.clone.kakao.dto.ChatRoomDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatRoom_id")
    private Long id;

    @Column
    private String roomId;

    @Column
    private String roomName;

    @Column
    private String participants;

    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private User user;

    @OneToMany(mappedBy = "chatRoom", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ChatMessage> chatMessageList = new ArrayList<>();

    public ChatRoom(ChatRoomDTO chatRoomDTO) {
        this.roomId = chatRoomDTO.getRoomId();
        this.roomName = chatRoomDTO.getName();
        this.participants = chatRoomDTO.getParticipants();
    }

    public void setChatRoom(User user) {
        this.user = user;
       user.getChatRoomList().add(this);
    }

    public static ChatRoom toChatRoomEntity(String roomName, String roomId){
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setRoomName(roomName);
        chatRoom.setRoomId(roomId);
       return chatRoom;
    }
}
