package ru.srfholding.trackermodels.converter.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * Тип задачи
 */
@Getter
@RequiredArgsConstructor
public enum TaskType {
    TASK(1, "Задача"),
    STORY(2, "Стори"),
    EPIC(3, "Эпик"),
    SUBTASK(4, "Подзадача"),
    BUG(5, "Дефект"),
    TECHNICAL(6, "Техническая");

    private final Integer code;
    private final String description;

    public static TaskType findTaskTypeByCode(Integer code) {
        if (code == null) {
            throw new IllegalArgumentException("Кода типа задачи не может быть пустым!");
        }

        return Arrays.stream(values())
                .filter(type -> code.equals(type.getCode()))
                .findAny()
                .orElse(null);
    }
}
