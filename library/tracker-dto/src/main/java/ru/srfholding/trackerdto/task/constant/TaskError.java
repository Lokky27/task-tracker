package ru.srfholding.trackerdto.task.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static ru.srfholding.trackerdto.task.constant.TaskErrorType.*;

/**
 * Ошибки сервиса задач
 */
@Getter
@RequiredArgsConstructor
public enum TaskError {
    INSUFFICIENT_RIGHTS(1003, BUSINESS, 403),
    DB_CONNECTION_FAILED(2003, TECHNICAL, 500),
    DEADLINE_INCORRECT(3001, VALIDATION, 400),
    TASK_NOT_FOUND(3002, BUSINESS, 404),
    ANOTHER_USER_MODIFICATION(3003, BUSINESS, 403),
    WRONG_CHANGE_STATUS(3004, BUSINESS, 409),
    INVALID_TASK_STATUS(3005, VALIDATION, 400),

    REDIS_UNAVAILABLE(7001, TECHNICAL, 503);

    private final Integer code;
    private final TaskErrorType errorType;
    private final Integer responseCode;
}
