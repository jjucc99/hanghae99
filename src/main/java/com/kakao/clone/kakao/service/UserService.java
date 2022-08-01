package com.kakao.clone.kakao.service;


import com.kakao.clone.kakao.dto.MainloginchecknameDto;
import com.kakao.clone.kakao.dto.LoginIdCheckDto;
import com.kakao.clone.kakao.dto.LoginRequestDto;
import com.kakao.clone.kakao.dto.SignupRequestDto;
import com.kakao.clone.kakao.model.User;
import com.kakao.clone.kakao.repository.UserRepository;
import com.kakao.clone.kakao.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private static final String ADMIN_TOKEN = "AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC";


    // 로그인
    public Boolean login(LoginRequestDto loginRequestDto){
        User member = userRepository.findByUsername(loginRequestDto.getUsername())
                .orElse(null);
        if (member != null) {
            if (!passwordEncoder.matches(loginRequestDto.getPassword(), member.getPassword())) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    // 회원가입
    public String registerUser(SignupRequestDto requestDto) {
        String error = "";
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();
        String checkPassword = requestDto.getCheckPassword();
        String nickname = requestDto.getNickname();
        String realname = requestDto.getRealname();
        String profileImage = requestDto.getProfileImage();

        String pattern = "^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}";

        // 회원 ID 중복 확인(추가부분 아이디 유효성 검사, )
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            return "중복된 id 입니다.";
        }
      /*  Optional<User> founds = userRepository.findByNickname(nickname);
        if (founds.isPresent()) {
            return "중복된 nickname 입니다.";
        }
*/
        // 회원가입 조건
        if (username.length() < 3) {
            return "아이디를 3자 이상 입력하세요.";
        } else if (!Pattern.matches(pattern, username)) {
            return "이메일 형식으로 입력 하세요.";
        } else if (!password.equals(checkPassword)) {
            return "비밀번호가 일치하지 않습니다.";
        } else if (password.length() < 4) {
            return "비밀번호를 4자 이상 입력하세요.";
        } else if (password.contains(username)) {
            return "비밀번호에 아이디를 포함할 수 없습니다.";
        }

        // 패스워드 인코딩
        password = passwordEncoder.encode(password);
        requestDto.setPassword(password);

        String encodeUserName = passwordEncoder.encode(username);


        // 유저 정보 저장
        User member = new User(username, password, nickname,encodeUserName,realname,profileImage);
        userRepository.save(member);
        return error;
    }

    //로그인 유저 정보 반환
    public User userInfo(UserDetailsImpl userDetails) {
        String username = userDetails.getUsername();
        String usernickname = userDetails.getUser().getNickname();
        User userinfo = new User(username, usernickname);
        return userinfo;
    }


        // 아이디 중복 체크
    public String userIdCheck(MainloginchecknameDto mainloginchecknameDto) {
        String pattern = "^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}";
        Optional<User> found = userRepository.findByUsername(mainloginchecknameDto.getUsername());
        if (mainloginchecknameDto.getUsername().length() < 3) {
            return "아이디를 3자 이상 입력하세요.";
        }
        else if (!Pattern.matches(pattern, mainloginchecknameDto.getUsername())) {
            return "이메일 형식으로 입력 하세요.";
        }
        else {
            if (found.isPresent()) {
                return "중복된 아이디 입니다.";
            } else {
                return "사용 할 수 있는 아이디 입니다.";
            }
        }
    }

        // 닉네임 중복 체크
    public String userNicNameCheck(LoginIdCheckDto loginIdCheckDto) {
        Optional<User> found = userRepository.findByNickname(loginIdCheckDto.getNickname());
        if (found.isPresent()) {
            return "중복된 닉네임 입니다.";
        }else{
            return "사용 할 수 있는 닉네임 입니다.";
        }
    }
}