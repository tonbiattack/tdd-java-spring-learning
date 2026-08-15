# 参照教材・Spring公式ガイドとの対応表

この表は、[Learn Go with Tests](https://quii.gitbook.io/learn-go-with-tests) の「テストを書きながら概念を学ぶ」という方針と、Spring公式のWebテストガイドを、Java Springの最小教材としてどう再構成したかを示します。**実装済み**は、章の説明、完成実装、対応テストがすべて揃う項目だけです。原典の文章・コードを転記または機械的に翻訳していません。

| 参照した主題 | Java Spring版の章 | 主な置換・対応 | 状態 | 根拠 |
|---|---|---|---|---|
| 小さなテストから学ぶ | 全章 | 「最初のテスト」「最小実装」「次の一歩」を各章に記載 | ★★★ 実装済み | 4章のガイドと対応テスト |
| 値と不正入力 | [第1章](java-fundamentals/01-task-title.md) | `String` のまま扱わず、`TaskTitle` 値オブジェクトへ置換 | ★★★ 実装済み | `TaskTitle`、`TaskTitleTest` |
| 依存の注入とテストダブル | [第2章](build-an-application/02-create-task-use-case.md) | Goのインターフェース利用を、Javaのポートと手書きテストダブルへ置換 | ★★★ 実装済み | `TaskRepository`、`TaskIdGenerator`、ユースケーステスト |
| HTTP/I/O | [第3章](build-an-application/03-task-controller.md) | Spring MVCの `@WebMvcTest` と `MockMvc` に置換 | ★★★ 実装済み | `TaskController`、`TaskControllerTest` |
| Springコンテナの起動 | [補足](questions-and-answers/01-test-strategy.md) | `@SpringBootTest` のスモークテストを追加 | ★★☆ 部分実装 | 起動確認のみ。DB接続は扱わない。 |
| Red → Green → Refactor の履歴 | 全章 | 学習者が再現できる手順をガイド化 | ★★☆ 部分実装 | 完成版リポジトリであり、各反復のGitコミットは未分離です。 |
| 並行性・キャンセル | 未着手 | Javaの仮想スレッド・非同期処理には展開しない | ☆☆☆ 未着手 | 範囲外 |

## 評価基準

| 評価 | 条件 |
|---|---|
| ★★★ 実装済み | 章の説明、完成コード、テストがある |
| ★★☆ 部分実装 | 近い題材はあるが、参照した論点の一部を省略・再構成している |
| ☆☆☆ 未着手 | 章または完成実装・テストがない |

## Java・Spring向けの置換判断

参照教材の対象はGoであり、本教材の対象はJava Springです。そのため、言語の基本構文を網羅する代わりに、Springアプリケーションで境界をどのようにテストするかへ焦点を移しました。Goの組み込みテスト機構に相当する位置にはJUnit Jupiterを置き、HTTPの検証には実サーバーを起動する代わりに `MockMvc` を使います。Spring公式ガイドも、Web層に対象を絞る `@WebMvcTest` を紹介しています。[2]

## 集計

| 評価 | 件数 |
|---|---:|
| ★★★ 実装済み | 4 |
| ★★☆ 部分実装 | 2 |
| ☆☆☆ 未着手 | 1 |

## 参考資料

[1] [Learn Go with Tests](https://quii.gitbook.io/learn-go-with-tests)  
[2] [Spring Guide — Testing the Web Layer](https://spring.io/guides/gs/testing-web)  
[3] [Spring Boot Reference — Testing Spring Boot Applications](https://docs.spring.io/spring-boot/reference/testing/spring-boot-applications.html)
