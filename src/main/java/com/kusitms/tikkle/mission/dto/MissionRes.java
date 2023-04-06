package com.kusitms.tikkle.mission.dto;

import com.kusitms.tikkle.mission.Day;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MissionRes {
    private Long id;
    private boolean isCheck;
    private String title;
    private boolean required;
    private Day day;
}
