package com.kakao.clone.kakao.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Friend {

    @Id  //ID 할당 방법 1.직접 넣는 방식 (Setter, 생성자) 2.(JPA나)DB에게 할당 책임을 전가. (@GenerateValue)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //친구 이름.
    private String friendName;
    private String profileImage;
    private String userStatus;
    private String nickname;
    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private User user;

    public Friend(User user, String friendName,String profileImage, String userStatus, String nickname) {
        this.user = user;
        this.friendName = friendName;
        this.profileImage = profileImage;
        this.userStatus = userStatus;
        this.nickname = nickname;
    }
}