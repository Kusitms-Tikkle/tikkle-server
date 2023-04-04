package com.kusitms.tikkle.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeRes {
    private Long id;
    private String image;
    private String intro;
}
