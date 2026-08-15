# 第2章：ユースケースを依存から分離する

## 目的

この章では、タスク作成の振る舞いを `CreateTaskUseCase` に置きます。ID生成と保存を具体的なUUID実装やデータベースへ直接結び付けず、`TaskIdGenerator` と `TaskRepository` というポートとして受け取ります。テストから固定IDと記録用リポジトリを渡すことで、SpringやDBを起動せずにユースケースを検証できます。

| 観点 | この章で決めること |
|---|---|
| 入力 | `CreateTaskCommand` が `title` を受け取ること |
| 出力 | `CreateTaskResult` が生成されたIDとタスク名を返すこと |
| 副作用 | 生成した `Task` を一度だけ保存すること |
| 依存 | ID生成と保存をポートとして注入すること |
| 完成実装 | `src/main/java/com/tonbiattack/tddspring/application/CreateTaskUseCase.java` |
| 完成テスト | `src/test/java/com/tonbiattack/tddspring/application/CreateTaskUseCaseTest.java` |

## 最初のテスト

保存されたタスクと返却結果の両方を確認します。ここではモックフレームワークではなく、保存内容を保持する小さな手書きテストダブルを使います。

```java
@Test
void タスクを保存して作成結果を返す() {
    RecordingTaskRepository repository = new RecordingTaskRepository();
    TaskIdGenerator idGenerator = () -> new TaskId("task-001");
    CreateTaskUseCase useCase = new CreateTaskUseCase(repository, idGenerator);

    CreateTaskResult result = useCase.handle(new CreateTaskCommand("見積書を送る"));

    assertThat(result).isEqualTo(new CreateTaskResult("task-001", "見積書を送る"));
    assertThat(repository.savedTasks()).singleElement();
}
```

## Greenにする最小実装

ユースケースは次の順番で値を組み立てます。入力から `TaskTitle` を作り、IDを生成し、`Task` を保存して、HTTPに依存しない結果を返します。保存先もIDの種類もユースケースは知りません。

```java
public CreateTaskResult handle(CreateTaskCommand command) {
    TaskTitle title = new TaskTitle(command.title());
    TaskId id = taskIdGenerator.generate();
    Task task = new Task(id, title);
    taskRepository.save(task);
    return new CreateTaskResult(task.id().value(), task.title().value());
}
```

完成実装では、コマンド自体が `null` でないことも確認しています。空白だけのタイトルでは `TaskTitle` の例外が発生し、`save` に到達しないことを、2本目のテストで確認します。

## Refactorで確認すること

「保存する」「IDを作る」といった副作用をユースケースへ直接埋め込まないでください。外部との境界を小さなインターフェースにすると、テストで固定値を使え、実装をJPAや外部IDサービスへ差し替えるときもユースケースのテストを保てます。

## 次に増やす振る舞い

重複したタスク名を拒否したい場合は、検索用のポートを追加します。その際は、既存タスクがあるときに「保存もID生成も行わない」という副作用の順序をテストで固定してください。

## 実行コマンド

```bash
./mvnw -Dtest=CreateTaskUseCaseTest test
```
