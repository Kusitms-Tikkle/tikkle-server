package com.kusitms.tikkle.account.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private String dateList;

    private double progressBar;

    @OneToOne
    @JoinColumn(name = "mbti_id")
    private Mbti mbti;

    @JsonIgnore
    @OneToMany(mappedBy = "account", orphanRemoval = true)
    private List<ParticipateChallenge> participateChallenges = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "account", orphanRemoval = true)
    private List<ParticipateMission> participateMissions = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "account", orphanRemoval = true)
    private List<Todo> todoList = new ArrayList<>();

    public static Account createAccount(String oAuthId, String email, String nickname) {
        return Account.builder()
                .oAuthId(oAuthId)
                .email(email)
                .oAuthNickName(nickname)
                .dateList("")
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

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public Account updateExtraInfo(String nickname, boolean checked, RoleType roletype, Status valid) {
        this.nickname = nickname;
        this.isChecked = checked;
        this.role = roletype;
        this.status = valid;
        return this;
    }

    public void setDateList(String date) {
        this.dateList += date;
    }

    public void resetDateList() {
        this.dateList = "";
    }

    public void setProgressBar(double progressBar) {
        this.progressBar = progressBar;
    }
}
