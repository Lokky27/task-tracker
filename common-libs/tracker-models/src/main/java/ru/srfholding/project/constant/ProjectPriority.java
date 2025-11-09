package ru.srfholding.project.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * Приоритеты проектов
 */
@Slf4j
@Getter
@RequiredArgsConstructor
public enum ProjectPriority {
    LOW(1, "LOW"),
    MEDIUM(2, "MEDIUM"),
    HIGH(3, "HIGH");

    private final Integer code;
    private final String description;

    public static ProjectPriority getByCode(Integer code) {
        if (code == null) {
            log.warn("Код атрибута не может быть пустым!");
            throw new IllegalArgumentException("Код атрибута не может быть пустым!");
        }

        return Arrays.stream(values())
                .filter(priority -> code.equals(priority.getCode()))
                .findAny()
                .orElse(null);
    }
}
