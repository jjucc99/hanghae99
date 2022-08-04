package com.kakao.clone.kakao.controller;

import com.kakao.clone.kakao.dto.ChatMessageDto;
import com.kakao.clone.kakao.dto.ChatMessageSaveDTO;
import com.kakao.clone.kakao.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class StompChatController {
    private final ChatService cs;

    //Client 가 SEND 할 수 있는 경로
    //stompConfig 에서 설정한 applicationDestinationPrefixes 와 @MessageMapping 경로가 병합됨
    //"/pub/chat/enter"
    @MessageMapping("/chat/enter")
    public ChatMessageDto enter(@Payload ChatMessageSaveDTO message,
                                @Header("Authorization") String jwt) {
        log.info("메시지 들어 왔니?");
        return cs.enterChatRoom(message);
    }

    @MessageMapping("/chat/message")
    public ChatMessageDto message(@Payload ChatMessageSaveDTO message,
                                  @Header("Authorization") String jwt) {
        log.info("메시지 들어 왔니?");
        return  cs.sendChat(message);
    }

//    @MessageMapping 을 통해 WebSocket 으로 들어오는 메세지 발행을 처리한다.
//    Client 에서는 prefix 를 붙여 "/pub/chat/enter"로 발행 요청을 하면
//    Controller 가 해당 메세지를 받아 처리하는데,
//    메세지가 발행되면 "/sub/chat/room/[roomId]"로 메세지가 전송되는 것을 볼 수 있다.
//    Client 에서는 해당 주소를 SUBSCRIBE 하고 있다가 메세지가 전달되면 화면에 출력한다.
//    이때 /sub/chat/room/[roomId]는 채팅방을 구분하는 값이다.
//    기존의 핸들러 ChatHandler 의 역할을 대신 해주므로 핸들러는 없어도 된다.
}