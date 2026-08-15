package com.tonbiattack.tddspring.adapter.web;

import com.tonbiattack.tddspring.application.CreateTaskCommand;
import com.tonbiattack.tddspring.application.CreateTaskResult;
import com.tonbiattack.tddspring.application.CreateTaskUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

/**
 * タスクに関するHTTP APIを提供するコントローラーです。
 */
@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final CreateTaskUseCase createTaskUseCase;

    public TaskController(CreateTaskUseCase createTaskUseCase) {
        this.createTaskUseCase = createTaskUseCase;
    }

    @PostMapping
    public ResponseEntity<TaskResponse> create(@Valid @RequestBody CreateTaskRequest request) {
        CreateTaskResult result = createTaskUseCase.handle(new CreateTaskCommand(request.title()));
        TaskResponse response = new TaskResponse(result.id(), result.title());
        return ResponseEntity.created(URI.create("/tasks/" + result.id())).body(response);
    }
}
