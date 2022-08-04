package com.kakao.clone.kakao.service;

import com.kakao.clone.kakao.Exception.CustomException;
import com.kakao.clone.kakao.Exception.ErrorCode;
import com.kakao.clone.kakao.dto.*;
import com.kakao.clone.kakao.model.Friend;
import com.kakao.clone.kakao.model.User;
import com.kakao.clone.kakao.repository.UserRepository;

import com.kakao.clone.kakao.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        List<User> userFriendGroup = userRepository.findByUsernameWithFriendUsingFetchJoin(userDetails.getUsername());
        List<User> userChatRoomGroup = userRepository.findByUsernameWithChatRoomUsingFetchJoin(userDetails.getUsername());

        List<FriendListDto> friendList = null;
        FriendResponseDto friendResponseDto;
        if (userFriendGroup != null) {
            friendList = new ArrayList<>();
            for (User temp : userFriendGroup) {
                FriendListDto friendListTemp = new FriendListDto(temp);
                friendList.add(friendListTemp);
            }

            friendResponseDto = new FriendResponseDto(userDto, friendList);
        }
        else
            friendResponseDto = new FriendResponseDto(userDto, friendList);

        return friendResponseDto;
    }


    @Transactional
    public CustomException friendNew(FriendNewRequertDto friendNewRequertDto, UserDetailsImpl userDetails){

        //등록할 친구의 정보
        User friendTemp = userRepository.findByUsername(friendNewRequertDto.getFriendname())
                .orElseThrow(()->new NullPointerException("값이 없음."));
        //로그인 유저 정보.
        User userTemp =  userDetails.getUser();

        if (userTemp.getUsername().equals(friendNewRequertDto.getFriendname()))
            return new CustomException(ErrorCode.SELF_REGISTRATION);

        // 로그인된 유저의 친구 정보
        List<User> userFriendGroup = userRepository.findByUsernameWithFriendUsingFetchJoin(userDetails.getUsername());
        List<User> userChatRoomGroup = userRepository.findByUsernameWithChatRoomUsingFetchJoin(userDetails.getUsername());

        for(User overlapUser : userFriendGroup) {
            if (overlapUser.getUsername().equals(friendNewRequertDto.getFriendname()))
                return new CustomException(ErrorCode.FRIENDNAME_OVERLAP);
        }

        //친구 정보 추가.
        userFriendGroup.add(friendTemp);

        //Friend 재배치.
        List<Friend> friendsInfo = new ArrayList<>();
        Friend temp = new Friend(friendTemp);
        friendsInfo.add(temp);

        UserReturnDto returnDtoDto = new UserReturnDto(userTemp,friendsInfo);
        User user = new User(returnDtoDto);
        userRepository.save(user);

        return new CustomException(ErrorCode.COMPLETED_OK);
    }


}
