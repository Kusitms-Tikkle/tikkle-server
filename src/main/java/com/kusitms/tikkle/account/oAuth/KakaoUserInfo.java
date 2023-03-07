package com.kusitms.tikkle.account.oAuth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KakaoUserInfo {
    Long id;
    String nickname;
    String email;
}
