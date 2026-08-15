# 第3章：Web層のHTTP契約をテストする

## 目的

この章では、タスク作成を `POST /tasks` として公開します。コントローラーの責務は、JSONを受け取り、入力を検証し、ユースケースを呼び、HTTPレスポンスへ変換することです。ドメインの作成規則をコントローラーへ重複させず、HTTP固有の契約だけを `@WebMvcTest` で検証します。

Spring公式ガイドは、Web層だけに対象を絞る `@WebMvcTest` と、リクエスト・レスポンスを検証するテスト方法を紹介しています。[1] この章でも同じ考え方で、`CreateTaskUseCase` をモックに置き換えます。

| 観点 | この章で決めること |
|---|---|
| エンドポイント | `POST /tasks` |
| 成功時 | `201 Created`、`Location` ヘッダー、JSONボディを返すこと |
| 不正入力 | 空白だけの `title` で `400 Bad Request` を返すこと |
| 単位 | コントローラーとSpring MVCの設定だけを起動すること |
| 完成実装 | `src/main/java/com/tonbiattack/tddspring/adapter/web/TaskController.java` |
| 完成テスト | `src/test/java/com/tonbiattack/tddspring/adapter/web/TaskControllerTest.java` |

## 最初のテスト

最初は、ユースケースが作成結果を返したときのHTTP応答を固定します。IDはユースケースが決めるため、Webテスト内では `task-001` を返すように設定します。

```java
when(createTaskUseCase.handle(any()))
        .thenReturn(new CreateTaskResult("task-001", "議事録を共有する"));

mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""{"title":"議事録を共有する"}"""))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "/tasks/task-001"))
        .andExpect(jsonPath("$.id").value("task-001"));
```

## Greenにする最小実装

`TaskController` はリクエスト型を受け取り、ユースケースの入力型へ変換します。成功時には `ResponseEntity.created` を使って作成されたリソースの位置を返します。

```java
@PostMapping
public ResponseEntity<TaskResponse> create(@Valid @RequestBody CreateTaskRequest request) {
    CreateTaskResult result = createTaskUseCase.handle(new CreateTaskCommand(request.title()));
    TaskResponse response = new TaskResponse(result.id(), result.title());
    return ResponseEntity.created(URI.create("/tasks/" + result.id())).body(response);
}
```

リクエスト型の `@NotBlank` と `@Size(max = 50)` は、HTTP境界で不正なJSON入力を早く弾くための検証です。ただし、同じ制約は第1章の `TaskTitle` にも置きます。HTTP以外からユースケースを呼んだ場合にも、ドメインの制約を守るためです。

## Refactorで確認すること

コントローラーのテストでドメインの細かな振る舞いまで重複検証しないでください。このテストが守るべきなのは、ステータス、ヘッダー、JSON、入力検証、ユースケース呼び出しの有無です。ドメイン制約は `TaskTitleTest`、保存の振る舞いは `CreateTaskUseCaseTest` が守ります。

## 次に増やす振る舞い

エラー形式をAPI仕様として統一するなら、`@RestControllerAdvice` を追加します。次のテストでは、バリデーションエラー時のJSONにフィールド名とメッセージが含まれることを確認してから、最小の例外ハンドラーを実装してください。

## 実行コマンド

```bash
./mvnw -Dtest=TaskControllerTest test
```

## 参照

[1] [Spring Guide — Testing the Web Layer](https://spring.io/guides/gs/testing-web)
