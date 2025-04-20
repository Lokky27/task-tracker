package ru.srfholding.trackerdto.base.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Статусы запросов
 */
@Getter
@RequiredArgsConstructor
public enum Status {
    SUCCESS(1),
    ERROR(2),
    UNKNOWN(3);

    private final int code;
}
