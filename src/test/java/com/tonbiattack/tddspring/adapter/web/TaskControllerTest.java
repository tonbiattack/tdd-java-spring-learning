package com.tonbiattack.tddspring.adapter.web;

import com.tonbiattack.tddspring.application.CreateTaskResult;
import com.tonbiattack.tddspring.application.CreateTaskUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CreateTaskUseCase createTaskUseCase;

    @Test
    void POSTでタスクを作成すると二百一を返す() throws Exception {
        when(createTaskUseCase.handle(any()))
                .thenReturn(new CreateTaskResult("task-001", "議事録を共有する"));

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"title":"議事録を共有する"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/tasks/task-001"))
                .andExpect(jsonPath("$.id").value("task-001"))
                .andExpect(jsonPath("$.title").value("議事録を共有する"));

        verify(createTaskUseCase).handle(any());
    }

    @Test
    void 空白だけのタスク名は四百を返しユースケースを呼ばない() throws Exception {
        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"title":"   "}
                                """))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(createTaskUseCase);
    }
}
