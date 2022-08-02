package com.kakao.clone.kakao.controller;


import com.kakao.clone.kakao.dto.ChatMessageDetailDTO;
import com.kakao.clone.kakao.dto.ChatRoomDetailDTO;
import com.kakao.clone.kakao.dto.UserDto;
import com.kakao.clone.kakao.security.UserDetailsImpl;
import com.kakao.clone.kakao.service.ChatRoomService;
import com.kakao.clone.kakao.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatController {
    private final ChatService cs;
    private final ChatRoomService chatRoomService;

    @PostMapping("/chatRoom/find")
    public String findRoomByUsername(@RequestBody UserDto userDto,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String roomId = chatRoomService.findChatRoom(userDto,userDetails);
        if (roomId.equals("")) {
            return "채팅 방이 존재하지 않습니다";
        }
        return roomId;
    }

    @PostMapping("/chatRoom/create")
    public String createChatRoom(@RequestBody UserDto userDto,
                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String roomId = chatRoomService.createChatRoom(userDto,userDetails);
        if (roomId.equals("")) {
            return "채팅 방을 생성하지 못했습니다.";
        }
        return roomId;
    }

    @PostMapping("/chatRoom/findAll")
    public List<ChatRoomDetailDTO> findAllRoom(@RequestBody UserDto userDto) {
        List<ChatRoomDetailDTO> chatRooms = chatRoomService.findAllChatRoom(userDto);
//        if (chatRoom.size() == 0) {
//            throw new IllegalArgumentException("채팅 방이 존재하지 않습니다");
//        }
        return chatRooms;
    }

    @GetMapping("/chatRoom/{roomId}")
    public List<ChatMessageDetailDTO> findChats(@PathVariable("roomId") String roomId) {
        List<ChatMessageDetailDTO> chats = chatRoomService.findChat(roomId);
        //        if (chats.size() == 0) {
//            throw new IllegalArgumentException("채팅 방이 존재하지 않습니다");
//        }
        return chats;
    }
}
