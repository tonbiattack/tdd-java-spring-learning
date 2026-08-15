package com.tonbiattack.tddspring.domain;

import java.util.Objects;

/**
 * タスク名の不変条件を保持する値オブジェクトです。
 */
public record TaskTitle(String value) {

    public static final int MAX_LENGTH = 50;

    public TaskTitle {
        Objects.requireNonNull(value, "タスク名は必須です");
        if (value.isBlank()) {
            throw new IllegalArgumentException("タスク名は空白だけにできません");
        }
        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("タスク名は%d文字以内にしてください".formatted(MAX_LENGTH));
        }
    }
}
