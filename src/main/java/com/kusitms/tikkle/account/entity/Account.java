package com.kusitms.tikkle.account.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kusitms.tikkle.account.dto.AccountInformReq;
import com.kusitms.tikkle.account.entity.enumtypes.OAuthType;
import com.kusitms.tikkle.account.entity.enumtypes.RoleType;
import com.kusitms.tikkle.account.entity.enumtypes.Status;
import com.kusitms.tikkle.mbti.Mbti;
import com.kusitms.tikkle.participate_challenge.ParticipateChallenge;
import com.kusitms.tikkle.participate_mission.ParticipateMission;
import com.kusitms.tikkle.todo.Todo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    private Integer profileImageIndex;

    private boolean isChecked;

    @OneToOne
    @JoinColumn(name = "mbti_id")
    private Mbti mbti;

    @JsonIgnore
    @OneToMany(mappedBy = "account")
    private List<ParticipateChallenge> participateChallenges = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "account")
    private List<ParticipateMission> participateMissions = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "account")
    private List<Todo> todoList = new ArrayList<>();

    public static Account createAccount(String oAuthId, String email, String nickname) {
        return Account.builder()
                .oAuthId(oAuthId)
                .email(email)
                .oAuthNickName(nickname)
                .build();
    }

    public void setAccountMbti(Mbti mbti) {
        this.mbti = mbti;
    }

    public void toggleValid(Status status) {
        if(this.status.equals(Status.VALID) && status.equals(Status.DELETED)) this.status = Status.DELETED;
        if(this.status.equals(Status.VALID) && status.equals(Status.LOGOUT)) this.status = Status.LOGOUT;
        if(this.status.equals(Status.LOGOUT)) this.status = status;
    }

    public void setAccountInfoByDto(AccountInformReq dto) {
        if (dto.getNickname() != null) this.nickname = dto.getNickname();

        //if (dto.getMbti() != null) this.mbti = dto.getMbti();

        if (dto.getProfileImageIndex() != null) this.profileImageIndex = dto.getProfileImageIndex();
    }

}
