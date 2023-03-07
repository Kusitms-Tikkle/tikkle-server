package com.kusitms.tikkle.account.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {
    private String nickname;
    private int profileImageIndex;
    private boolean isChecked;

    public LoginRequest(String nickname, int profileImageIndex, boolean isChecked) {
        this.nickname = nickname;
        this.profileImageIndex = profileImageIndex;
        this.isChecked = isChecked;
    }
}
