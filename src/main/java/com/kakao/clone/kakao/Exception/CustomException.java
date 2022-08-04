package com.kakao.clone.kakao.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public class CustomException extends RuntimeException{
    private final ErrorCode errorCode;
}
