package com.kusitms.tikkle.participate_challenge;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kusitms.tikkle.account.entity.Account;
import com.kusitms.tikkle.challenge.Challenge;
import com.kusitms.tikkle.participate_mission.ParticipateMission;
import com.kusitms.tikkle.todo.Todo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ParticipateChallenge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participate_challenge_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    @JsonIgnore
    @OneToMany(mappedBy = "participateChallenge", orphanRemoval = true)
    private List<ParticipateMission> participateMissions = new ArrayList<>();

    // 연관관계 편의 메서드
    public void setAccount(Account account) {
        this.account = account;
        account.getParticipateChallenges().add(this);
    }


    public static ParticipateChallenge createParticipateChallenge(Account account, Challenge challenge) {
        ParticipateChallenge p = new ParticipateChallenge();
        p.setAccount(account);
        p.challenge = challenge;
        return p;
    }
}
