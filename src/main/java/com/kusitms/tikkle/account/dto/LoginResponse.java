package com.kusitms.tikkle.account.dto;

import com.kusitms.tikkle.account.entity.Account;
import com.kusitms.tikkle.account.entity.enumtypes.RoleType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponse {
    private String responseType;
    private Long id;
    private RoleType role;
    private String accessToken;

    public LoginResponse(String responseType, Account account, String accessToken){
        this.responseType = responseType;
        this.id = account.getId();
        this.role = account.getRole();
        this.accessToken = accessToken;
    }
}
