# 検証記録

## 実行環境

| 項目 | 検証時の値 |
|---|---|
| 実行日 | 2026-08-15 |
| Java | OpenJDK 21.0.11 |
| Maven Wrapper | Maven 3.9.11 |
| Spring Boot | 3.5.7 |
| 実行OS | Ubuntu 24.04 |

## 自動テスト

次のコマンドを実行しました。

```bash
./mvnw test
```

結果は **8 tests run, 0 failures, 0 errors, 0 skipped** でした。確認したテストの役割は次のとおりです。

| テストクラス | 件数 | 確認した振る舞い |
|---|---:|---|
| `TaskTitleTest` | 3 | 正常な名前、空白のみ、50文字上限 |
| `CreateTaskUseCaseTest` | 2 | 作成・保存、空白名では保存しないこと |
| `TaskControllerTest` | 2 | `201 Created` のHTTP契約、`400 Bad Request` の入力検証 |
| `TddSpringLearningApplicationTests` | 1 | Springアプリケーションコンテキストの起動 |

## 起動確認

アプリケーションを `./mvnw spring-boot:run` で起動し、次の要求を送信しました。

```bash
curl -i -X POST http://localhost:8080/tasks \
  -H 'Content-Type: application/json' \
  -d '{"title":"議事録を共有する"}'
```

HTTPステータスは `201 Created`、`Location: /tasks/{UUID}` が返ることを確認しました。テストのJSONアサーションでは、応答本文に作成された `id` と入力した `title` が含まれることも確認しています。

## 再検証手順

```bash
./mvnw test
./mvnw spring-boot:run
```

別のターミナルから `POST /tasks` を実行し、201応答を確認してください。
