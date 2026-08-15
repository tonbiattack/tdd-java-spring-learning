package com.tonbiattack.tddspring.domain;

/**
 * タスクIDを採番するためのポートです。
 */
public interface TaskIdGenerator {

    TaskId generate();
}
