package com.kakao.clone.kakao.repository;


import com.kakao.clone.kakao.model.User;
import lombok.Builder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByNickname(String NickName);
    @Query("SELECT distinct t FROM User t join fetch t.friends")
    public List<User> findByUsernameWithFriendUsingFetchJoin(String username);

    @Query("SELECT distinct t FROM User t join fetch t.chatRoomList")
    public List<User> findByUsernameWithChatRoomUsingFetchJoin(String username);
}