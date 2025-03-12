package ru.srfholding.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.srfholding.service.TaskService;
import ru.srfholding.trackerdto.task.CreateTaskRequestDto;
import ru.srfholding.trackerdto.task.response.GetTaskListResponseDto;
import ru.srfholding.trackerdto.task.response.GetTaskResponseDto;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<GetTaskListResponseDto> getTasks() {
        return ResponseEntity.ok(taskService.getTasks());
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<GetTaskResponseDto> getTaskById(@PathVariable UUID taskId) {
        return ResponseEntity.ok(taskService.getTaskById(taskId));
    }

    @PostMapping("/create")
    public ResponseEntity<GetTaskResponseDto> createTask(@RequestBody CreateTaskRequestDto createTaskRequestDto) {
        GetTaskResponseDto createdTask = taskService.createTask(createTaskRequestDto);
        return ResponseEntity.ok(createdTask);
    }
}
