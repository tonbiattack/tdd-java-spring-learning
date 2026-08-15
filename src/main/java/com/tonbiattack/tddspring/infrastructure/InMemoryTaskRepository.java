package com.tonbiattack.tddspring.infrastructure;

import com.tonbiattack.tddspring.domain.Task;
import com.tonbiattack.tddspring.domain.TaskRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * 学習用のインメモリ保存実装です。
 */
@Repository
public class InMemoryTaskRepository implements TaskRepository {

    private final List<Task> tasks = new ArrayList<>();

    @Override
    public void save(Task task) {
        tasks.add(task);
    }
}
