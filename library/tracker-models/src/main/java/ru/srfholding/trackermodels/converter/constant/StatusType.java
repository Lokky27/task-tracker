package ru.srfholding.trackermodels.converter.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;


/**
 * Типы статусов задач
 */
@Getter
@RequiredArgsConstructor
public enum StatusType {
    NEW(1, "Новый", List.of(1, 8, 9)),
    OPEN(2, "Открыт", List.of(3, 8, 9)),
    IN_PROGRESS(3, "В разарботке", List.of(4, 7, 8, 9, 11)),
    IN_REVIEW(4, "На ревью", List.of(3, 5, 8, 9)),
    READY_FOR_TEST(5, "Готов к тестированию", List.of(6, 3)),
    TESTING(6, "Тестирование", List.of(3, 11, 7, 8, 9)),
    RESOLVED(7, "Решен", List.of(10, 12)),
    CANCELED(8, "Отменен", List.of(10, 12)),
    DECLINED(9, "Отклонен", List.of(10, 12)),
    CLOSED(10, "Закрыт", List.of(12)),
    POSTPONE(11, "Отложен", List.of(3, 8, 9)),
    REOPEN(12, "Переоткрыт", List.of(3, 8, 9));

    private final Integer code;
    private final String description;
    private final List<Integer> nextStatusCode;

    public static StatusType findStatusByCode(Integer code) {
        if (code == null) {
            throw new IllegalArgumentException("Код статуса не может быть пустым!");
        }

        return Arrays.stream(values())
                .filter(status -> code.equals(status.getCode()))
                .findAny()
                .orElse(null);
    }
}
