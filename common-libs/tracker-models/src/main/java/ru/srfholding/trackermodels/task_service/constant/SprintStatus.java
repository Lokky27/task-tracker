package ru.srfholding.trackermodels.task_service.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * Статусы спринта
 */
@Getter
@RequiredArgsConstructor
public enum SprintStatus {
    PLANNING(1, "Планируется"),
    BACKLOG(2, "В бэклоге"),
    IN_PROGRESS(3, "В работе"),
    DONE(4, "Завершен"),
    CANCELED(5, "Отменен 1");

    private final Integer code;
    private final String description;

    public static SprintStatus getByCode(Integer statusCode) {

        return Arrays.stream(values())
                .filter(status -> statusCode.equals(status.getCode()))
                .findAny()
                .orElse(null);
    }
}
