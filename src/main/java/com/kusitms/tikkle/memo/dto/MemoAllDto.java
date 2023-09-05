package com.kusitms.tikkle.memo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemoAllDto {
    public Long id;
    public String content;
    public String imageUrl;
    public String nickname;
    public String missionTitle;
}
