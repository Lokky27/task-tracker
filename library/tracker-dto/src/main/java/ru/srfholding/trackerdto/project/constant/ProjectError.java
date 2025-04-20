package ru.srfholding.trackerdto.project.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.srfholding.trackerdto.base.response.ErrorsType;
import ru.srfholding.trackerdto.base.response.TrackerErrors;

import static ru.srfholding.trackerdto.base.response.ErrorsType.BUSINESS;
import static ru.srfholding.trackerdto.base.response.ErrorsType.VALIDATION;

/**
 * Ошибки сервиса проектов
 */
@Getter
@RequiredArgsConstructor
public enum ProjectError implements TrackerErrors {
    PROJECT_NAME_IS_EMPTY("5001", "Название проекта не может быть пустым", VALIDATION),
    CREATE_PROJECT_FIELDS_INVALID("5002", "Не заполнены обязательные поля", VALIDATION),
    PROJECT_OWNER_NOT_FOUND("4001", "Владелец проекта не найден в системе", BUSINESS);

    private final String code;
    private final String description;
    private final ErrorsType type;
}
