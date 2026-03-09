package com.mosquito.mosquitowiki.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "잘못된 입력입니다"),

    // User
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다"),
    EMAIL_DUPLICATED(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다"),
    EMAIL_ALREADY_REGISTERED_WITH_OTHER_PROVIDER(HttpStatus.CONFLICT, "다른 프로바이더로 가입한 이메일입니다");

    private final HttpStatus status;
    private final String message;
}
