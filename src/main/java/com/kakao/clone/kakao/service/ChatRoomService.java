package com.kakao.clone.kakao.service;

import com.kakao.clone.kakao.dto.*;
import com.kakao.clone.kakao.model.ChatMessage;
import com.kakao.clone.kakao.model.ChatRoom;
import com.kakao.clone.kakao.model.Usertable;
import com.kakao.clone.kakao.repository.ChatRepository;
import com.kakao.clone.kakao.repository.ChatRoomRepository;
import com.kakao.clone.kakao.repository.UserRepository;
import com.kakao.clone.kakao.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatService cs;
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRepository chatRepository;

    @Transactional
    public String findChatRoom(ChatDto chatDto, UserDetailsImpl userDetails) {
        // 유저가 존재하는 지 검증한다.
        Usertable user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다"));

        // 채팅방이 존재하는 지 검증한다.
        ChatRoom chatRoom = chatRoomRepository
                .findByUser_UsernameAndAndParticipants(userDetails.getUsername(), chatDto.getParticipants())
                .orElseThrow(() -> new RuntimeException("채팅 방을 찾을 수 없습니다"));

        // 모든 검증에서 통과하면 룸 ID를 리턴한다.
        return chatRoom.getRoomId();
    }
    @Transactional
    public String createChatRoom(ChatDto chatDto, UserDetailsImpl userDetails) {
        String roomId = UUID.randomUUID().toString();

        // 채팅방이 존재하는 지 검증한다.
        boolean isPresent = chatRoomRepository
                .findByUser_UsernameAndAndParticipants(userDetails.getUsername(), chatDto.getParticipants())
                .isPresent();

        if (isPresent) {
            throw new RuntimeException("이미 존재하는 채팅방입니다.");
        }

        // 유저를 위한 채팅룸
        ChatRoomDTO UserChatRoomDTO = new ChatRoomDTO(roomId, chatDto.getParticipants(), chatDto.getRoomName());
        ChatRoom UserRoom = new ChatRoom(UserChatRoomDTO);
        Usertable user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다"));
        UserRoom.setChatRoom(user);

        //초대한 친구를 위한 채팅룸
        ChatRoomDTO ParticipantsChatRoomDTO = new ChatRoomDTO(roomId, userDetails.getUsername(), chatDto.getRoomName());
        ChatRoom ParticipantsRoom = new ChatRoom(ParticipantsChatRoomDTO);
        Usertable participants = userRepository.findByUsername(chatDto.getParticipants())
                .orElseThrow(() -> new RuntimeException("친구를 찾을 수 없습니다"));
        ParticipantsRoom.setUser(participants);

        // 생성한 채팅 룸을 저장한다.
        chatRoomRepository.save(UserRoom);
        chatRoomRepository.save(ParticipantsRoom);

        //저장에 성공했으면 룸 ID를 리턴
        return UserRoom.getRoomId();
    }

    @Transactional
    public List<ChatRoomDetailDTO> findAllChatRoom(ChatDto chatDto, UserDetailsImpl userDetails) {
        // Dto를 넣기 위한 리스트
        List<ChatRoomDetailDTO> chatRoomDetailDTOS = new ArrayList<>();

        // 채팅 방을 전부 찾는다.
        List<ChatRoom> roomList = chatRoomRepository.findAllByUser_Username(userDetails.getUsername());

        // 채팅방 들을 Dto 에 넣어서 보관한다.
        for (ChatRoom chatRoom : roomList) {
            chatRoomDetailDTOS.add(ChatRoomDetailDTO.toChatRoomDetailDTO(chatRoom));
        }
        return chatRoomDetailDTOS;
    }

    public List<ChatMessageDetailDTO> findChat(String roomId) {
        // 채팅을 찾는다.
        List<ChatMessage> chats = chatRepository.findAllByChatRoom_roomId(roomId);

        // 찾은 채팅을 Dto 로 변환시켜준다.
        List<ChatMessageDetailDTO> chatDto = new ArrayList<>();
        for (ChatMessage chat : chats) {
            chatDto.add(ChatMessageDetailDTO.toChatMessageDetailDTO(chat));
        }
        
        //반환
        return chatDto;
    }
}
