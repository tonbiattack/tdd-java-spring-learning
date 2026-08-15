# 補足：この教材のテスト戦略

## 結論

この教材では、テスト対象の責務に応じて起動範囲を分けます。ドメインとユースケースではSpringを起動せず、Web層では `@WebMvcTest`、最後の配線確認だけで `@SpringBootTest` を使います。すべてを同じ種類のテストにすると、失敗の原因と実行時間の両方が分かりにくくなります。

| テスト | 守るもの | Spring起動 | 主な依存の扱い |
|---|---|---|---|
| `TaskTitleTest` | 値の不変条件 | なし | なし |
| `CreateTaskUseCaseTest` | タスク作成と保存の振る舞い | なし | 手書きテストダブル |
| `TaskControllerTest` | HTTPステータス、ヘッダー、JSON、入力検証 | Web層のみ | `CreateTaskUseCase` をモック |
| `TddSpringLearningApplicationTests` | コンポーネントの配線 | アプリケーション全体 | 実装をそのまま利用 |

## なぜ `@SpringBootTest` だけにしないのか

`@SpringBootTest` はSpringアプリケーションコンテキストを使うテストのために提供されており、デフォルトではサーバーを起動せずにテスト環境を準備します。[1] 配線確認には有用ですが、値オブジェクトやユースケースの小さな規則まで全体起動に頼ると、失敗したときに原因を局所化しづらくなります。

この教材では、最初に最小のテストで設計を決め、境界へ近づくほど起動範囲を増やします。これは高速性だけでなく、「今このテストが何を保証しているか」を明確にするための選択です。

## どのテストから書くか

新しい振る舞いを追加するときは、次の順番を出発点にしてください。

| 変更の例 | 最初に書くテスト | 次に確認するテスト |
|---|---|---|
| タスク名の制約追加 | `TaskTitleTest` | `CreateTaskUseCaseTest` |
| 保存前の業務規則追加 | `CreateTaskUseCaseTest` | `TaskControllerTest` |
| 新しいHTTPエンドポイント追加 | `TaskControllerTest` | ユースケーステスト、必要なら全体起動テスト |
| DBスキーマやSQLの変更 | リポジトリ実装の統合テスト | ユースケース・Web層の既存テスト |

完成コードを変更したら、まず該当する狭いテストを実行し、次に `./mvnw test` で全体を確認します。テストの種類を減らすのではなく、重複した保証を避けながら役割を分けます。

## 実行コマンド

```bash
./mvnw test
```

## 参照

[1] [Spring Boot Reference — Testing Spring Boot Applications](https://docs.spring.io/spring-boot/reference/testing/spring-boot-applications.html)
