# 調査メモ

## 目的

本教材は、Java SpringでTDDの入り口を学ぶためのオリジナルの最小サンプルを作ることを目的にしています。参照資料の構造・狙いを確認し、コードや文章を複製せずに、JavaとSpringで自然な概念へ置き換えました。

| 確認項目 | 調査結果 | 教材への反映 |
|---|---|---|
| TDD教材の進め方 | Learn Go with Testsは、テストを書きながら言語とTDDの基礎を学ぶことを目的にしています。[1] | 各章に最初のテスト、最小実装、次の一歩を置きました。 |
| Springの全体テスト | Spring Bootは `@SpringBootTest` を提供し、アプリケーションコンテキストを起動してテストできます。[2] | 配線確認だけを行うスモークテストを1本追加しました。 |
| SpringのWeb層テスト | Spring公式ガイドは、`@WebMvcTest` によりWeb層を絞ってテストする方法を示しています。[3] | HTTP契約の検証に `@WebMvcTest` と `MockMvc` を採用しました。 |
| 参照資料のライセンス・帰属 | Learn Go with TestsのリポジトリはMITライセンスです。[4] | 原典名とURLを明記し、原文・コードを転記しない方針にしました。 |

## 既存リポジトリとの重複確認

選択済みの記事リポジトリでは、Springのトランザクション、アノテーション、Javaの例外などを扱う公開記事が確認できました。一方、Java SpringでTDDを値オブジェクトからWeb層テストまで段階的に学ぶ記事は確認できなかったため、本教材は既存テーマと直接重複しません。

## 再利用上の方針

参照資料は学習の進め方と概念選定のために利用し、本教材の章名、例題、実装、説明文は独自に作成しました。Spring公式資料はテストアノテーションと用途の確認に使い、API設計・テストコードは本教材の要件に合わせて作成しています。

## 参照

[1] [Learn Go with Tests — Learn test-driven development with Go](https://quii.gitbook.io/learn-go-with-tests)  
[2] [Spring Boot Reference — Testing Spring Boot Applications](https://docs.spring.io/spring-boot/reference/testing/spring-boot-applications.html)  
[3] [Spring Guide — Testing the Web Layer](https://spring.io/guides/gs/testing-web)  
[4] [quii/learn-go-with-tests — LICENSE.md](https://github.com/quii/learn-go-with-tests/blob/main/LICENSE.md)
