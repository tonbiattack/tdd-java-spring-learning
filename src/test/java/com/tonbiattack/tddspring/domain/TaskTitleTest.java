package com.tonbiattack.tddspring.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class TaskTitleTest {

    @Test
    void タスク名を保持できる() {
        TaskTitle title = new TaskTitle("請求書を確認する");

        assertThat(title.value()).isEqualTo("請求書を確認する");
    }

    @Test
    void 空白だけのタスク名は拒否する() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new TaskTitle("   "))
                .withMessage("タスク名は空白だけにできません");
    }

    @Test
    void 五十文字を超えるタスク名は拒否する() {
        String tooLongTitle = "あ".repeat(TaskTitle.MAX_LENGTH + 1);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> new TaskTitle(tooLongTitle))
                .withMessage("タスク名は50文字以内にしてください");
    }
}
