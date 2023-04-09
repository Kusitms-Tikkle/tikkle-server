package com.kusitms.tikkle.mission;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kusitms.tikkle.challenge.Challenge;
import com.kusitms.tikkle.participate_mission.ParticipateMission;
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
public class Mission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mission_id")
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private Day day;

    private boolean required;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;
}
