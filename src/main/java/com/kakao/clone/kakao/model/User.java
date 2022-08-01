package com.kakao.clone.kakao.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "USERS")
public class User {

    @Id  //ID 할당 방법 1.직접 넣는 방식 (Setter, 생성자) 2.(JPA나)DB에게 할당 책임을 전가. (@GenerateValue)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false, unique = true)
    private String encodeUserName;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String nickname;
    @Column(nullable = false)
    private String realname;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)//mappedBy 연관관계의 주인이 아니다(나는 FK가 아니에요) DB에 컬럼 만들지 마세요.
    @JsonIgnoreProperties({"user"})
    private List<Friend> friends = new ArrayList<>();

    @Column
    private String userStatus;

    @Column(length = 65535)
    private String profileImage;

    @Column(length = 65535)
    private String profileBgImage;

    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"user"})
    private List<ChatRoom> chatRoomList;

    public User(String username, String password,
                String nickname, String encodeUserName, String realname, String profileImage) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.encodeUserName = encodeUserName;
        this.realname = realname;
        this.profileImage = profileImage;
    }

    public User(String username, String usernickname) {
        this.username = username;
        this.nickname = usernickname;
    }

    public User(String username,     String password,
                String nickname,     String encodeUserName,String realname,
                String profileImage, List<ChatRoom> chatRoomList, List<Friend> friends) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.encodeUserName = encodeUserName;
        this.realname = realname;
        this.profileImage = profileImage;
    }
}