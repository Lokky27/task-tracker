package ru.srfholding.trackerdto.users.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.srfholding.trackerdto.base.response.ErrorsType;
import ru.srfholding.trackerdto.base.response.TrackerErrors;

import static ru.srfholding.trackerdto.base.response.ErrorsType.*;

@Getter
@RequiredArgsConstructor
public enum UserServiceError implements TrackerErrors {
    USER_PROFILE_ALREADY_EXISTS("USR_001", "Профиль уже существует", BUSINESS),
    USER_PROFILE_NOT_FOUND("USR_002", "Профиль не найден", BUSINESS),
    ACCESS_DENIED_FOR_UPDATE("USR_003", "Нет доступа к изменению", BUSINESS),
    FIELD_VALUES_UNEXPECTED("USR_004", "Недопустимые значения полей", VALIDATION),
    AUTH_SERVICE_UNAVAILABLE("USR_005", "Ошибка обращения к Auth Service", TECHNICAL);

    private final String code;
    private final String description;
    private final ErrorsType type;
}
