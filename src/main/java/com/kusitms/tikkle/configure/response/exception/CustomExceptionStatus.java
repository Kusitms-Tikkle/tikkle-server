package com.kusitms.tikkle.configure.response.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
@Getter
@RequiredArgsConstructor
public enum CustomExceptionStatus {

    // Common
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    NOT_AUTHENTICATED_ACCOUNT(false, 2004, "로그인이 필요합니다."),

    // users
    ACCOUNT_NOT_FOUND(false, 2010, "사용자를 찾을 수 없습니다."),

    // OAuth
    OAUTH_EMPTY_INFORM(false, 2560, "OAuth에 필요한 정보가 누락되었습니다."),

    // challenge
    CHALLENGE_NOT_FOUND(false, 2020, "챌린지를 찾을 수 없습니다."),

    // mbti
    MBTI_NOT_FOUND(false, 2030, "MBTI를 찾을 수 없습니다."),

    // home

    //s3
    S3_UPLOAD_FAIL(false, 2050, "이미지 업로드에 실패했습니다."),
    FILE_WRONG_TYPE(false, 2051,  "잘못된 형식의 파일입니다.");



    private final boolean isSuccess;
    private final int code;
    private final String message;
}
