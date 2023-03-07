package com.kusitms.tikkle.configure.response.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
@Getter
@RequiredArgsConstructor
public enum CustomExceptionStatus {

    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    NOT_AUTHENTICATED_ACCOUNT(false, 2004, "로그인이 필요합니다."),

    OAUTH_EMPTY_INFORM(false, 2560, "OAuth에 필요한 정보가 누락되었습니다.");

    private final boolean isSuccess;
    private final int code;
    private final String message;
}
