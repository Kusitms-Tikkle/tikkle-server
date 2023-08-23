package com.kusitms.tikkle.memo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemoWithTodoResponseDto {
    private Long todoId;
    private boolean isChecked;
    private String title;
    private String color;
    private MemoDto memo;

    public MemoWithTodoResponseDto(Long id, boolean isChecked, String title, String color) {
        this.todoId = id;
        this.isChecked = isChecked;
        this.title = title;
        this.color = color;
    }
}
