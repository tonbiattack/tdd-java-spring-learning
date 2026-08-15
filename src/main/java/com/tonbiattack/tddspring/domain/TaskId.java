package com.tonbiattack.tddspring.domain;

import java.util.Objects;

/**
 * タスクIDを表す値オブジェクトです。
 */
public record TaskId(String value) {

    public TaskId {
        Objects.requireNonNull(value, "タスクIDは必須です");
        if (value.isBlank()) {
            throw new IllegalArgumentException("タスクIDは空白だけにできません");
        }
    }
}
