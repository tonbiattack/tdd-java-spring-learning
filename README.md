# TDDで学ぶJava Spring：タスク作成API

[Learn Go with Tests](https://quii.gitbook.io/learn-go-with-tests) の「小さなテストから概念を固める」方針を、Java 21 と Spring Boot 3.5 に合わせて再構成した学習教材です。小さな振る舞いテストを一つ書き、最小の実装で通し、読みやすく整える **Red → Green → Refactor** を、タスク作成APIを題材に反復します。[1]

> このリポジトリは各章の完成版です。章のMarkdownを読み、完成実装を隠すか削除して、最初のテストが失敗する状態から自分で再現してください。

## ねらい

この教材では、いきなり `@SpringBootTest` でアプリケーション全体を起動するのではなく、ドメイン、ユースケース、Web層の順に対象範囲を広げます。Spring Boot は用途に応じてテスト用のアノテーションを提供しており、Web層だけを対象にする `@WebMvcTest` もその一つです。[2] [3]

| 項目 | この教材で行うこと |
|---|---|
| 言語・ランタイム | Java 21、Spring Boot 3.5、Maven |
| テスト | JUnit Jupiter、AssertJ、Mockito、MockMvc |
| 非同期・並行性 | この最小教材では扱いません。同期的なHTTP要求と依存の差し替えに集中します。 |
| アプリケーション境界 | 値オブジェクト、ユースケース、リポジトリ・ID生成ポート、HTTP API |
| 発展 | 永続化、認可、一覧・更新・削除、例外応答の統一を後続の練習として追加できます。 |

## 前提

JDK 21 とインターネット接続が必要です。Maven Wrapperを同梱しているため、Mavenを別途導入せずに実行できます。IDEを使う場合は、プロジェクトをMavenプロジェクトとして読み込んでください。

## はじめ方

```bash
git clone https://github.com/tonbiattack/tdd-java-spring-learning.git
cd tdd-java-spring-learning
./mvnw test
./mvnw spring-boot:run
```

テストは `./mvnw test` で実行します。アプリケーションを起動した後、次の要求でタスクを作成できます。

```bash
curl -i -X POST http://localhost:8080/tasks \
  -H 'Content-Type: application/json' \
  -d '{"title":"議事録を共有する"}'
```

成功時は `201 Created`、`Location: /tasks/{id}`、作成された `id` と `title` を含むJSONを返します。`title` が空白だけ、または50文字を超える場合は、リクエスト検証により `400 Bad Request` を返します。

## 学び方

各章では、まず「最初のテスト」だけを読んでください。次に完成実装を隠すか削除して、失敗を確認します。そのテストを通す最小のコードを書き、緑になってから重複・命名・責務を整えます。最後に「次に増やす振る舞い」を一つだけ追加します。実装を先に読み切らず、テストの要求に従って進むことが重要です。

## 目次

### 基礎：ドメインを小さく始める

| # | 章 | 主な完成実装 |
|---:|---|---|
| 1 | [値オブジェクトでタスク名の不変条件を表す](java-fundamentals/01-task-title.md) | `TaskTitle`、`TaskTitleTest` |

### アプリケーション：HTTP APIへ広げる

| # | 章 | 主な完成実装 |
|---:|---|---|
| 2 | [ユースケースを依存から分離する](build-an-application/02-create-task-use-case.md) | `CreateTaskUseCase`、`CreateTaskUseCaseTest` |
| 3 | [Web層のHTTP契約をテストする](build-an-application/03-task-controller.md) | `TaskController`、`TaskControllerTest` |

### 補足：テストの役割を選ぶ

| # | 章 | 主な完成実装 |
|---:|---|---|
| 4 | [この教材のテスト戦略](questions-and-answers/01-test-strategy.md) | `TddSpringLearningApplicationTests` |

## コード配置

| パス | 内容 |
|---|---|
| `src/main/java/` | 完成実装 |
| `src/test/java/` | 振る舞いテスト |
| `java-fundamentals/` | 基礎章ガイド |
| `build-an-application/` | アプリケーション章ガイド |
| `questions-and-answers/` | 補足章ガイド |
| `DESIGN.md` | Java・Spring向けの置換判断 |
| `coverage-matrix.md` | 参照教材・公式ガイドとの対応状況 |
| `docs/research-notes.md` | 参照資料と教材設計の調査メモ |
| `docs/verification.md` | 実行環境と検証結果 |

## 参考資料

[1] [Learn Go with Tests — Learn test-driven development with Go](https://quii.gitbook.io/learn-go-with-tests)  
[2] [Spring Boot Reference — Testing Spring Boot Applications](https://docs.spring.io/spring-boot/reference/testing/spring-boot-applications.html)  
[3] [Spring Guide — Testing the Web Layer](https://spring.io/guides/gs/testing-web)
