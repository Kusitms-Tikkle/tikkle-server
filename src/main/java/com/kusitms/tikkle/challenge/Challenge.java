package com.kusitms.tikkle.challenge;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kusitms.tikkle.mission.Mission;
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
public class Challenge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "challenge_id")
    private Long id;

    private String mbti;

    private String title;

    private String intro;

    private String color;

    private String shortIntro;

    private String imageUrl;

    private boolean required;

    @JsonIgnore
    @OneToMany(mappedBy = "challenge")
    private List<Mission> missionList = new ArrayList<>();
}