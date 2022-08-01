package com.kakao.clone.kakao.repository;


import com.kakao.clone.kakao.model.Usertable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Usertable, Long> {
    Optional<Usertable> findByUsername(String username);
    Optional<Usertable> findByNickname(String NickName);
}