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
    private int sticker1;
    private int sticker2;
    private int sticker3;
}
