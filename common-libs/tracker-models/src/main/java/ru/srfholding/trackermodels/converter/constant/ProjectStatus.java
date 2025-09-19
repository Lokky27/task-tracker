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
    NEW(1, "Новый проект"),
    IN_PROGRESS(2, "В разработке"),
    CANCELED(3, "Отменен"),
    DONE(4, "Завершен"),
    ARCHIVED(5, "В архиве");

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
