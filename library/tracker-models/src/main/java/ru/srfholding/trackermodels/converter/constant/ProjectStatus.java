package ru.srfholding.trackermodels.converter.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * Статусы проета
 */
@Getter
@RequiredArgsConstructor
public enum ProjectStatus {
    NEW(1, "Новый проект");

    private final Integer code;
    private final String description;

    /**
     * Поиск статуса по коду
     * @param code - код статуса
     * @return статус проекта
     */
    public static ProjectStatus findStatusByCode(Integer code) {
        return Arrays.stream(values())
                .filter(status -> code.equals(status.getCode()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Значения по коду " + " не найдено"));
    }
}
