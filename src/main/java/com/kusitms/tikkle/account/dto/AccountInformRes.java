package com.kusitms.tikkle.account.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountInformRes {
    private Long id;
    private String nickname;
    private String label;
    private String imageUrl;
}
