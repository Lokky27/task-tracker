package ru.srfholding.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.srfholding.service.TaskService;
import ru.srfholding.trackerdto.base.response.TrackerResponse;
import ru.srfholding.trackerdto.task.ChangeStatusTaskRequest;
import ru.srfholding.trackerdto.task.CreateTaskRequestDto;
import ru.srfholding.trackerdto.task.response.ChangeStatusTaskBody;
import ru.srfholding.trackerdto.task.response.TaskResult;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<List<TrackerResponse<TaskResult>>> getTasks(@RequestHeader("rqId") String rqUid,
                                                                      @RequestHeader("rqTm") String rqTm) {

        return ResponseEntity.ok(taskService.getTasks());
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TrackerResponse<TaskResult>> getTaskById(
            @RequestHeader("rqId") String rqId,
            @RequestHeader("rqTm") Instant rqTm,
            @PathVariable UUID taskId
    ) {

        return ResponseEntity.ok(taskService.getTaskById(taskId));
    }

    @PostMapping("/create")
    public ResponseEntity<TrackerResponse<TaskResult>> createTask(@RequestBody CreateTaskRequestDto createTaskRequestDto) {
        TrackerResponse<TaskResult> task = taskService.createTask(createTaskRequestDto);

        return ResponseEntity.ok(task);
    }

    @PutMapping("/{taskId}/status")
    public ResponseEntity<TrackerResponse<ChangeStatusTaskBody>> changeStatus(
            @PathVariable UUID taskId,
            @RequestBody ChangeStatusTaskRequest changeStatusTaskRequest) {

        return ResponseEntity.ok(taskService.changeTaskStatus(taskId, changeStatusTaskRequest));
    }
}
