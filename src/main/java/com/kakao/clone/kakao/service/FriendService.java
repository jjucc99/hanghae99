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
        //로그인 유저 정보.
        User userTemp = userRepository.findById(userDetails.getUser().getId())
                .orElseThrow(()->new CustomException(ErrorCode.EMPTY_CONTENT));

        //유저정보 추출
        FriendListDto userDto = new FriendListDto(userTemp.getUsername(),userTemp.getProfileImage(),
                userTemp.getUserStatus(),userTemp.getNickname());

        List<Friend> friendList = userTemp.getFriends();
        List<FriendListDto> friendListDtos = new ArrayList<>();
        FriendResponseDto friendResponseDto = null;

        if (friendList != null) {
            for (Friend temp : friendList) {
                FriendListDto friendListTemp = new FriendListDto(temp.getFriendName(),temp.getProfileImage(),
                        temp.getUserStatus(),temp.getNickname());
                friendListDtos.add(friendListTemp);
            }
            friendResponseDto = new FriendResponseDto(userDto, friendListDtos);
            return friendResponseDto;
        }
        else
            return null;
    }


    @Transactional
    public CustomException friendNew(FriendNewRequertDto friendNewRequertDto, UserDetailsImpl userDetails){

        //등록할 친구의 정보
        User friendTemp = userRepository.findByUsername(friendNewRequertDto.getFriendname())
                .orElseThrow(()->new CustomException(ErrorCode.EMPTY_CONTENT));

        //로그인 유저 정보.
        User userTemp = userRepository.findById(userDetails.getUser().getId())
                .orElseThrow(()->new CustomException(ErrorCode.EMPTY_CONTENT));

        //등록 이름과 로그인 이름이 같으면. return
        if (userTemp.getUsername().equals(friendNewRequertDto.getFriendname()))
            return new CustomException(ErrorCode.SELF_REGISTRATION);

        List<Friend> friend = userTemp.getFriends();
        for(Friend overlapUser : friend) {
            if (overlapUser.getFriendName().equals(friendNewRequertDto.getFriendname()))
                return new CustomException(ErrorCode.FRIENDNAME_OVERLAP);
        }

        //Friend 재배치.
        List<Friend> friendsInfo = new ArrayList<>();
        Friend temp = new Friend(userTemp,friendTemp.getUsername(),
                friendTemp.getProfileImage(),friendTemp.getUserStatus(),friendTemp.getNickname());
        friendsInfo.add(temp);

        UserReturnDto returnDtoDto = new UserReturnDto(userTemp,friendsInfo);
        User user = new User(returnDtoDto);
        userRepository.save(user);

        return new CustomException(ErrorCode.COMPLETED_OK);
    }


}
