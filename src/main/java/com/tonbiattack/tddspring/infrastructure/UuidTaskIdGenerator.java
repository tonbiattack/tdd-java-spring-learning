package com.tonbiattack.tddspring.infrastructure;

import com.tonbiattack.tddspring.domain.TaskId;
import com.tonbiattack.tddspring.domain.TaskIdGenerator;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * UUIDでタスクIDを生成する実装です。
 */
@Component
public class UuidTaskIdGenerator implements TaskIdGenerator {

    @Override
    public TaskId generate() {
        return new TaskId(UUID.randomUUID().toString());
    }
}
