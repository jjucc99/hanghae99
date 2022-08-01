/*
package com.kakao.clone.kakao.Main;

import com.sparta.selecthing.model.Board;
import com.sparta.selecthing.repository.BoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MainService {

    private final BoardRepository boardRepository;

    public MainService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Transactional
    public List<MainResponseDto> showMains() {
        List<Board> boardAll = boardRepository.findAll();
        List<MainResponseDto> mainResponseDtoList = new ArrayList<>();
        for(Board temp : boardAll)
        {
            MainResponseDto mainResponseDtoTemp = new MainResponseDto(temp.getId(),
                    temp.getTitle(),temp.getImage(),temp.getContent(),
                    temp.getMember(),temp.getMbti());
            mainResponseDtoList.add(mainResponseDtoTemp);
        }

    return mainResponseDtoList;
    }
}
*/
