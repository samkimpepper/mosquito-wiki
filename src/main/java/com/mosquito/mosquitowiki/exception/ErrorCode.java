package com.mosquito.mosquitowiki.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "잘못된 입력입니다"),
    FILE_UPLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 업로드에 실패했습니다"),

    // User
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다"),
    EMAIL_DUPLICATED(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다"),
    EMAIL_ALREADY_REGISTERED_WITH_OTHER_PROVIDER(HttpStatus.CONFLICT, "다른 프로바이더로 가입한 이메일입니다"),

    // Brand
    BRAND_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 존재하는 브랜드입니다"),
    BRAND_NOT_FOUND(HttpStatus.NOT_FOUND, "브랜드를 찾을 수 없습니다"),

    // Product
    PRODUCT_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 존재하는 제품입니다");



    private final HttpStatus status;
    private final String message;
}
