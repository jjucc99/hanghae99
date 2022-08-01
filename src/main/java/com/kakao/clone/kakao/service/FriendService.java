package com.kakao.clone.kakao.service;

import com.kakao.clone.kakao.dto.*;
import com.kakao.clone.kakao.model.Friend;
import com.kakao.clone.kakao.model.User;
import com.kakao.clone.kakao.repository.UserRepository;

import com.kakao.clone.kakao.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor //생성자 미리 생성.
public class FriendService {
    private final UserRepository userRepository;

    public FriendResponseDto showFriendList(UserDetailsImpl userDetails) {
        //유저정보 추출
        FriendListDto userDto = new FriendListDto(userDetails.getUser());

        //친구 목록 추출
        List<Friend> friends = userDetails.getUser().getFriends();


        List<FriendListDto> friendList = null;
        FriendResponseDto friendResponseDto;
        if (friends != null) {
            friendList = new ArrayList<>();
            for (Friend temp : friends) {
                FriendListDto friendListTemp = new FriendListDto(temp.getUser());
                friendList.add(friendListTemp);
            }

            friendResponseDto = new FriendResponseDto(userDto, friendList);
        }
        else
            friendResponseDto = new FriendResponseDto(userDto, friendList);

        return friendResponseDto;
    }


    @Transactional
    public UserReturnDto friendNew(FriendNewRequertDto friendNewRequertDto, UserDetailsImpl userDetails){

        //등록할 친구의 정보
        User friendTemp= userRepository.findByUsername(friendNewRequertDto.getFriendname())
                .orElseThrow(()->new NullPointerException("값이 없음."));
        Friend friend = new Friend(friendTemp);

        //로그인된 유저 정보.
        User user = userDetails.getUser();
        //로그인 된 사람의 친구 정보
        List<Friend> userFriendGroup =  user.getFriends();

        //친구 임시저장.
        userFriendGroup.add(friend);

        user.setFriends(userFriendGroup);

        //data 저장
        userRepository.save(user);

        userDetails = new UserDetailsImpl(user);
        UserReturnDto returnDtoDto = new UserReturnDto(user);
        return returnDtoDto;
    }


}
