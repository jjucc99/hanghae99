package com.kakao.clone.kakao.repository;

import com.kakao.clone.kakao.model.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findByRoomIdAndAndUser_Username(String roomId, String writer);
    Optional<ChatRoom> findByUser_UsernameAndAndParticipants(String username, String participants);
    List<ChatRoom> findAllByUser_Username(String username);
}
