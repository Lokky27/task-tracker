package ru.srfholding.trackermodels.converter.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * Тип приоритета
 */
@Getter
@RequiredArgsConstructor
public enum PriorityType {
    TRIVIAL(1, "Тривиальный"),
    MINOR(2, "Минорный"),
    MAJOR(3, "Мажорный"),
    CRITICAL(4, "Критический"),
    BLOCKER(5, "Блокер");

    private final Integer code;
    private final String description;

    /**
     * Получить приоритет по коду
     * @param code - код приоритета
     * @return приоритет
     */
    public static PriorityType findPriorityByCode(Integer code) {
        return Arrays.stream(values())
                .filter(priority -> code.equals(priority.getCode()))
                .findAny()
                .orElse(null);
    }
}
