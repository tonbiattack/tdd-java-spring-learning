package com.tonbiattack.tddspring.domain;

import java.util.Objects;

/**
 * 作成済みタスクを表すエンティティです。
 */
public record Task(TaskId id, TaskTitle title) {

    public Task {
        Objects.requireNonNull(id, "タスクIDは必須です");
        Objects.requireNonNull(title, "タスク名は必須です");
    }
}
