package com.kusitms.tikkle.challenge.dto;

import com.kusitms.tikkle.mission.dto.MissionRes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeDetailRes {
    private Long id;
    private String imageUrl;
    private String title;
    private String intro;
    private boolean participate;
    private List<MissionRes> missionList;
}