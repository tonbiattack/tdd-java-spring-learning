package com.tonbiattack.tddspring.domain;

/**
 * タスクを保存するためのポートです。
 */
public interface TaskRepository {

    void save(Task task);
}
