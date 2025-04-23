package ru.srfholding.trackerdto.task.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.srfholding.trackerdto.base.response.ErrorsType;
import ru.srfholding.trackerdto.base.response.TrackerErrors;

import static ru.srfholding.trackerdto.base.response.ErrorsType.*;


/**
 * Ошибки сервиса задач
 */
@Getter
@RequiredArgsConstructor
public enum TaskError implements TrackerErrors {
    CREATE_TASK_FIELDS_VALIDATION("1001", VALIDATION, "Ошибка валидации по полю(-ям): "),
    INSUFFICIENT_RIGHTS("1003", BUSINESS, "Недостаточно прав доступа"),
    DB_CONNECTION_FAILED("2003", TECHNICAL, "Ошибка соединения с БД"),
    DEADLINE_INCORRECT("3001", VALIDATION, "Некореектный дедлайн"),
    TASK_NOT_FOUND("3002", BUSINESS, "Задача не найдена в системе"),
    ANOTHER_USER_MODIFICATION("3003", BUSINESS, "Недостаточно прав для перевода статуса"),
    WRONG_CHANGE_STATUS("3004", BUSINESS, "Попытка перевода в неверный статус"),
    INVALID_TASK_STATUS("3005", VALIDATION, "Некорректный статус"),
    PROJECT_NOT_FOUND("3006", BUSINESS, "Проект не найден"),
    ASSIGNEE_NOT_FOUND("3007", BUSINESS, "Назначенный пользователь не найден в системе"),
    REDIS_UNAVAILABLE("7001", TECHNICAL, "Сервер Redis не доступен");

    private final String code;
    private final ErrorsType type;
    private final String description;
}
