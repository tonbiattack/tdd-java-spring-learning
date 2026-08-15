# 設計方針

この教材は、Javaの文法を網羅することではなく、Springアプリケーションでテストから設計を進める際の判断を小さなAPIで経験することを目的にしています。実装対象は「タスクを一件作成する」だけに絞り、値の制約、依存の境界、HTTP契約を段階的に増やします。

## 構造

```text
HTTP request
    │
    ▼
TaskController ──> CreateTaskUseCase ──> TaskRepository
                         │
                         └─────────────> TaskIdGenerator
    │
    ▼
HTTP response
```

コントローラーはHTTPの入力検証と応答の組み立てを担当します。ユースケースはドメイン型を組み立て、保存とID生成をポートとして受け取ります。これにより、ユースケースのテストはSpringコンテナもデータベースも起動せずに実行できます。

| 設計上の問い | この教材での選択 | 選択理由 |
|---|---|---|
| タスク名の制約をどこに置くか | `TaskTitle` 値オブジェクト | 空白のみ・50文字超過を、HTTP以外の呼び出しからも守るためです。 |
| ID生成をどうテストするか | `TaskIdGenerator` ポート | UUIDのランダム性をテストへ持ち込まず、固定IDを渡せるためです。 |
| 保存をどうテストするか | `TaskRepository` ポートと手書きテストダブル | 振る舞いを明示でき、永続化技術とユースケースの関心を分離できるためです。 |
| HTTPをどこまで起動するか | `@WebMvcTest` と `MockMvc` | Web層だけを対象にし、HTTPステータス・ヘッダー・JSONを高速に検証するためです。[1] |
| 全体起動をいつ確認するか | `@SpringBootTest` のスモークテスト | コンポーネントの配線が壊れていないことを最後に確認するためです。[2] |

## 意図的に採用しなかったもの

この教材ではJPA、H2、認証、例外応答の独自形式、非同期処理を採用しません。初学時にそれらを一度に導入すると、テスト失敗がドメインの振る舞いによるものか、Spring設定や外部資源によるものかが分かりにくくなるためです。

この構成は本番アーキテクチャの雛形ではありません。保存要件が具体化した時点で、リポジトリ実装をJPAなどへ置き換え、マイグレーションと統合テストを追加してください。ユースケースのテストを維持していれば、置換の影響範囲を小さく保てます。

## 参照

[1] [Spring Guide — Testing the Web Layer](https://spring.io/guides/gs/testing-web)  
[2] [Spring Boot Reference — Testing Spring Boot Applications](https://docs.spring.io/spring-boot/reference/testing/spring-boot-applications.html)
