package com.kusitms.tikkle.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeRecommendRes {
    private String nickname;
    private String label;
    private String imageUrl;
    private String intro;
    private List<ChallengeRes> challengeList;
}
