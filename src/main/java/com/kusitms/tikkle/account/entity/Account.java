package com.kusitms.tikkle.account.entity;

import com.kusitms.tikkle.account.entity.enumtypes.OAuthType;
import com.kusitms.tikkle.account.entity.enumtypes.RoleType;
import com.kusitms.tikkle.account.entity.enumtypes.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    private String email;

    @Enumerated(EnumType.STRING)
    private OAuthType oAuth;

    private String oAuthId;

    private String oAuthNickName;

    private String nickname;

    private int profileImageIndex;

    private boolean isChecked;

    public static Account createAccount(String oAuthId, String email, String nickname) {
        return Account.builder()
                .oAuthId(oAuthId)
                .email(email)
                .oAuthNickName(nickname)
                .build();
    }

    public void toggleValid(Status status) {
        if(this.status.equals(Status.VALID) && status.equals(Status.DELETED)) this.status = Status.DELETED;
        if(this.status.equals(Status.VALID) && status.equals(Status.LOGOUT)) this.status = Status.LOGOUT;
    }
}
