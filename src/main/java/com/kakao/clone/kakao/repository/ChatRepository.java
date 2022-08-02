package com.kakao.clone.kakao.repository;

import com.kakao.clone.kakao.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findAllByChatRoom_roomId(String chatRoom_roomId);
}
