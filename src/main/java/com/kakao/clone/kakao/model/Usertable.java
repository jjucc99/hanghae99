package com.kakao.clone.kakao.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Usertable {

    @Id  //ID 할당 방법 1.직접 넣는 방식 (Setter, 생성자) 2.(JPA나)DB에게 할당 책임을 전가. (@GenerateValue)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false, unique = true)
    private String encodeUserName;

    @Column
    private String userStatus;

    @Column
    private String profileImage;

    @Column
    private String profileBgImage;



    public Usertable(String username, String password, String nickname,String encodeUserName) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.encodeUserName = encodeUserName;
    }


    public Usertable(String username, String usernickname) {
        this.username = username;
        this.nickname = usernickname;
    }
}
