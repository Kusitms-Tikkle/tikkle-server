package com.kusitms.tikkle.memo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemoWithTodoResponseDto {
    private Long todoId;
    private String title;
    private String color;
    private MemoDto memo;

    public MemoWithTodoResponseDto(Long id, String title, String color) {
        this.todoId = id;
        this.title = title;
        this.color = color;
    }
}
