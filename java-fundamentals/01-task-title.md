# 第1章：値オブジェクトでタスク名の不変条件を表す

## 目的

この章では、タスク名を単なる `String` ではなく、制約を自分で守る `TaskTitle` として表現します。HTTPリクエストの検証だけに依存すると、バッチや別のユースケースから生成したときに同じ制約を守れません。ドメイン側にも不変条件を置く理由を、最小のテストで確認します。

| 観点 | この章で決めること |
|---|---|
| 正常系 | 有効な名前を保持できること |
| 境界値 | 50文字までは受け入れ、51文字は拒否すること |
| 不正入力 | 空白だけの名前を拒否すること |
| 完成実装 | `src/main/java/com/tonbiattack/tddspring/domain/TaskTitle.java` |
| 完成テスト | `src/test/java/com/tonbiattack/tddspring/domain/TaskTitleTest.java` |

## 最初のテスト

最初は「有効な名前を保持できる」だけをテストします。まだ `TaskTitle` がなければコンパイルエラーになり、最初のRedになります。

```java
@Test
void タスク名を保持できる() {
    TaskTitle title = new TaskTitle("請求書を確認する");

    assertThat(title.value()).isEqualTo("請求書を確認する");
}
```

## Greenにする最小実装

この時点では、Javaの `record` で値を保持するだけでテストを通せます。

```java
public record TaskTitle(String value) {
}
```

次に空白だけの入力を拒否するテストを追加します。`String#isBlank` を使って空白文字だけの値を区別し、例外メッセージもテストで固定します。最後に、50文字という上限を定数にして境界値のテストを追加します。

## Refactorで確認すること

制約を増やした後でも、`TaskTitle` の生成箇所に同じ検証コードを複製しないでください。入力検証の責務を値オブジェクトへ集め、他の層は `TaskTitle` が生成できたことを前提に扱います。

## 次に増やす振る舞い

削除・更新を追加する場合は、名前を変更する操作を `Task` エンティティに置くか、`TaskTitle` を差し替えるかを検討します。その前に、空白名で変更しようとしたときに既存のタスクの状態が変わらないことをテストしてください。

## 実行コマンド

```bash
./mvnw -Dtest=TaskTitleTest test
```
