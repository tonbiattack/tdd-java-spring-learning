package com.tonbiattack.tddspring.application;

import com.tonbiattack.tddspring.domain.Task;
import com.tonbiattack.tddspring.domain.TaskId;
import com.tonbiattack.tddspring.domain.TaskIdGenerator;
import com.tonbiattack.tddspring.domain.TaskRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class CreateTaskUseCaseTest {

    @Test
    void タスクを保存して作成結果を返す() {
        RecordingTaskRepository repository = new RecordingTaskRepository();
        TaskIdGenerator idGenerator = () -> new TaskId("task-001");
        CreateTaskUseCase useCase = new CreateTaskUseCase(repository, idGenerator);

        CreateTaskResult result = useCase.handle(new CreateTaskCommand("見積書を送る"));

        assertThat(result).isEqualTo(new CreateTaskResult("task-001", "見積書を送る"));
        assertThat(repository.savedTasks())
                .singleElement()
                .satisfies(task -> {
                    assertThat(task.id().value()).isEqualTo("task-001");
                    assertThat(task.title().value()).isEqualTo("見積書を送る");
                });
    }

    @Test
    void 空白だけのタスク名は保存しない() {
        RecordingTaskRepository repository = new RecordingTaskRepository();
        TaskIdGenerator idGenerator = () -> new TaskId("task-001");
        CreateTaskUseCase useCase = new CreateTaskUseCase(repository, idGenerator);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> useCase.handle(new CreateTaskCommand(" ")))
                .withMessage("タスク名は空白だけにできません");

        assertThat(repository.savedTasks()).isEmpty();
    }

    private static final class RecordingTaskRepository implements TaskRepository {
        private final List<Task> savedTasks = new ArrayList<>();

        @Override
        public void save(Task task) {
            savedTasks.add(task);
        }

        List<Task> savedTasks() {
            return List.copyOf(savedTasks);
        }
    }
}
