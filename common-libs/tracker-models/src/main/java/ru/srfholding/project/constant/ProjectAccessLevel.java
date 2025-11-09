package ru.srfholding.project.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.srfholding.common.exception.AttributeCodeIsNullException;

import java.util.stream.Stream;

/**
 * Уровень доступа к публичной ссылке
 */
@Slf4j
@Getter
@RequiredArgsConstructor
public enum ProjectAccessLevel {
    DENIED(1, "DENIED"),
    VIEWER(2, "VIEWER"),
    ADMIN(3, "ADMIN");

    private final Integer code;
    private final String description;

    public static ProjectAccessLevel getByCode(Integer accessLevelCode) {
        if (accessLevelCode == null) {
            log.warn("Код уровня доступа пуст!");
            throw new AttributeCodeIsNullException("Код уровня доступа пуст!");
        }

        return Stream.of(values())
                .filter(level -> accessLevelCode.equals(level.getCode()))
                .findAny()
                .orElse(null);
    }
}
