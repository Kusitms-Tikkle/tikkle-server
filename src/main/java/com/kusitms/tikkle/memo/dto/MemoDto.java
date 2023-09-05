package com.kusitms.tikkle.memo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemoDto {
    private Long memoId;
    private String content;
    private String image;
    private boolean isPrivate;
    private Long sticker1;
    private Long sticker2;
    private Long sticker3;
}
