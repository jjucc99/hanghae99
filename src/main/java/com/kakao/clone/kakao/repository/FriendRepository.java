package com.kakao.clone.kakao.repository;


import com.kakao.clone.kakao.model.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {
/*    @Query("SELECT distinct t FROM User t join fetch t.friends ")
    public List<Friend> findAllById(Long id);*/
}