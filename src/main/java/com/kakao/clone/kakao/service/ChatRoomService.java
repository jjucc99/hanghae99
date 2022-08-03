package com.kakao.clone.kakao.service;


import com.kakao.clone.kakao.Exception.CustomException;
import com.kakao.clone.kakao.Exception.ErrorCode;
import com.kakao.clone.kakao.dto.*;
import com.kakao.clone.kakao.model.ChatMessage;
import com.kakao.clone.kakao.model.ChatRoom;
import com.kakao.clone.kakao.model.User;
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
    public ChatResponseDto findChatRoom(UserDto userDto, UserDetailsImpl userDetails) {
        // UserDto을 통해서 유저를 찾는다.
        String username = userDetails.getUser().getUsername();

        // 유저가 존재하는 지 검증한다.
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다"));

        // 채팅방이 존재하는 지 검증한다.
        ChatRoom chatRoom = chatRoomRepository
                .findByUser_UsernameAndAndParticipants(userDetails.getUser().getUsername(), userDto.getParticipants())
                .orElseThrow(() -> new CustomException(ErrorCode.CAN_NOT_CREATE_ROOM));

        // 모든 검증에서 통과하면 룸 ID를 리턴한다.
        return new ChatResponseDto(chatRoom.getRoomId(), userDetails.getUsername());
    }
    @Transactional
    public ChatResponseDto createChatRoom(UserDto userDto, UserDetailsImpl userDetails) {
        String roomId = UUID.randomUUID().toString();

        // 채팅방이 존재하는 지 검증한다.
        boolean isPresent = chatRoomRepository
                .findByUser_UsernameAndAndParticipants(userDetails.getUsername(), userDto.getParticipants())
                .isPresent();

        if (isPresent) {
            throw new CustomException(ErrorCode.DUPLICATE_CHAT_ROOM);
        }

        // 유저를 위한 채팅룸
        ChatRoomDTO UserChatRoomDTO = new ChatRoomDTO(roomId, userDto.getParticipants(), userDto.getRoomName());
        ChatRoom UserRoom = new ChatRoom(UserChatRoomDTO);
        User user = userRepository.findByUsername(userDetails.getUser().getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        UserRoom.setChatRoom(user);

        //초대한 친구를 위한 채팅룸
        ChatRoomDTO ParticipantsChatRoomDTO = new ChatRoomDTO(roomId, userDetails.getUser().getUsername(), userDto.getRoomName());
        ChatRoom ParticipantsRoom = new ChatRoom(ParticipantsChatRoomDTO);
        User participants = userRepository.findByUsername(userDto.getParticipants())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        ParticipantsRoom.setUser(participants);

        // 생성한 채팅 룸을 저장한다.
        chatRoomRepository.save(UserRoom);
        chatRoomRepository.save(ParticipantsRoom);



        //저장에 성공했으면 Dto을 리턴
        return new ChatResponseDto(UserRoom.getRoomId(), userDetails.getUsername());
    }

    @Transactional
    public List<ChatRoomDetailDTO> findAllChatRoom(UserDto userDto, UserDetailsImpl userDetails ) {
        // Dto를 넣기 위한 리스트
        List<ChatRoomDetailDTO> chatRoomDetailDTOS = new ArrayList<>();

        // 채팅 방을 전부 찾는다.
        List<ChatRoom> roomList = chatRoomRepository.findAllByUser_Username(userDetails.getUser().getUsername());

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
