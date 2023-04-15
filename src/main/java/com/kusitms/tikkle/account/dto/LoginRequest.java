package com.kusitms.tikkle.account.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {
    private String nickname;
    private boolean isChecked;

    public LoginRequest(String nickname, boolean isChecked) {
        this.nickname = nickname;
        this.isChecked = isChecked;
    }
}
