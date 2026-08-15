package com.tonbiattack.tddspring.adapter.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * タスク作成APIのリクエストです。
 */
public record CreateTaskRequest(
        @NotBlank(message = "titleは必須です")
        @Size(max = 50, message = "titleは50文字以内にしてください")
        String title
) {
}
