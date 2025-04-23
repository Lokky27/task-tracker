package ru.srfholding.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestHeader;
import ru.srfholding.exception.AssigneeNotFoundException;
import ru.srfholding.exception.ProjectNotFoundException;
import ru.srfholding.exception.TaskNotFoundException;
import ru.srfholding.trackerdto.base.ResponseBuilder;
import ru.srfholding.trackerdto.base.response.TrackerResponse;

import static ru.srfholding.trackerdto.task.constant.TaskError.*;

@Slf4j
@ControllerAdvice
public class GlobalTaskServiceControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<TrackerResponse<Void>> handleValidationException(MethodArgumentNotValidException e,
                                                                           @RequestHeader("rqUid") String rqUid,
                                                                           @RequestHeader("rqTm") String rqTm) {
        log.warn("Ошибка валидации запроса rqUid: [{}] - [{}]", rqUid, e.getMessage());

        return ResponseEntity.badRequest()
                .body(ResponseBuilder.<Void>error(rqUid, rqTm)
                        .withError(CREATE_TASK_FIELDS_VALIDATION)
                        .build());
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<TrackerResponse<Void>> handleTaskNotFoundException(TaskNotFoundException e,
                                                                             @RequestHeader("rqUid") String rqUid,
                                                                             @RequestHeader("rqTm") String rqTm) {
        log.warn("rqUid: [{}]. Ошибка получения задачи из БД", rqUid, e);
        return ResponseEntity.badRequest()
                .body(ResponseBuilder.<Void>error(rqUid, rqTm)
                        .withError(TASK_NOT_FOUND)
                        .build());
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<TrackerResponse<Void>> handleProjectNotFoundException(ProjectNotFoundException e,
                                                                                @RequestHeader("rqUid") String rqUid,
                                                                                @RequestHeader("rqTm") String rqTm) {
        log.warn("rqUid: [{}]. Проект не найден в системе", rqUid, e);

        return ResponseEntity
                .badRequest()
                .body(ResponseBuilder.<Void>error(rqUid, rqTm)
                        .withError(PROJECT_NOT_FOUND)
                        .build());
    }

    @ExceptionHandler(AssigneeNotFoundException.class)
    public ResponseEntity<TrackerResponse<Void>> handleAssigneeNotFoundException(AssigneeNotFoundException e,
                                                                                 @RequestHeader("rqUid") String rqUid,
                                                                                 @RequestHeader("rqTm") String rqTm) {
        log.warn("rqUid: [{}]. Назначенный пользователь не найден в системе", rqUid, e);

        return ResponseEntity
                .badRequest()
                .body(ResponseBuilder.<Void>error(rqUid, rqTm)
                        .withError(ASSIGNEE_NOT_FOUND)
                        .build());
    }
}
