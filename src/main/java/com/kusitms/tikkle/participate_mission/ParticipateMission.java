package com.kusitms.tikkle.participate_mission;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kusitms.tikkle.account.entity.Account;
import com.kusitms.tikkle.mission.Mission;
import com.kusitms.tikkle.participate_challenge.ParticipateChallenge;
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
public class ParticipateMission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participate_mission_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private Mission mission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participate_challenge_id")
    private ParticipateChallenge participateChallenge;

    @JsonIgnore
    @OneToMany(mappedBy = "participateMission", orphanRemoval = true)
    private List<Todo> todoList = new ArrayList<>();

    public void setAccount(Account account) {
        this.account = account;
        account.getParticipateMissions().add(this);
    }

    public void setParticipateChallenge(ParticipateChallenge pc) {
        this.participateChallenge = pc;
        pc.getParticipateMissions().add(this);
    }

    public static ParticipateMission createParticipateMission(Account account, Mission mission, ParticipateChallenge participateChallenge) {
        ParticipateMission pm = new ParticipateMission();
        pm.setAccount(account);
        pm.mission = mission;
        pm.setParticipateChallenge(participateChallenge);
        return pm;
    }
}
