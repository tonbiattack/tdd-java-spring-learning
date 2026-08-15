package com.tonbiattack.tddspring.application;

import com.tonbiattack.tddspring.domain.Task;
import com.tonbiattack.tddspring.domain.TaskId;
import com.tonbiattack.tddspring.domain.TaskIdGenerator;
import com.tonbiattack.tddspring.domain.TaskRepository;
import com.tonbiattack.tddspring.domain.TaskTitle;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * タスクを作成して保存するユースケースです。
 */
@Service
public class CreateTaskUseCase {

    private final TaskRepository taskRepository;
    private final TaskIdGenerator taskIdGenerator;

    public CreateTaskUseCase(TaskRepository taskRepository, TaskIdGenerator taskIdGenerator) {
        this.taskRepository = Objects.requireNonNull(taskRepository);
        this.taskIdGenerator = Objects.requireNonNull(taskIdGenerator);
    }

    public CreateTaskResult handle(CreateTaskCommand command) {
        Objects.requireNonNull(command, "作成コマンドは必須です");

        TaskId id = taskIdGenerator.generate();
        TaskTitle title = new TaskTitle(command.title());
        Task task = new Task(id, title);
        taskRepository.save(task);

        return new CreateTaskResult(task.id().value(), task.title().value());
    }
}
