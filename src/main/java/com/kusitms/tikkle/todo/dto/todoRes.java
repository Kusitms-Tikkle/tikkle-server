package com.kusitms.tikkle.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class todoRes {
    private Long id;
    private String title;
    private boolean isChecked;
    private String color;
}
