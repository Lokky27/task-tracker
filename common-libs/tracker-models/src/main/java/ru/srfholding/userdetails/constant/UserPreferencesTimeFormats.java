package ru.srfholding.userdetails.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * Типы времени
 */
@Slf4j
@Getter
@RequiredArgsConstructor
public enum UserPreferencesTimeFormats {
    TWELVE_HOURS_FORMAT(1, "12ч"),
    TWENTY_FOUR_HOURS_FORMAT(2, "24ч");

    private final Integer code;
    private final String description;

    public static UserPreferencesTimeFormats getPreferencesByCode(Integer code) {
        if (code == null) {
            log.warn("Код формата времени не может быть пустым!");
            throw new IllegalArgumentException("Код формата времени не может быть пустым!");
        }

        return Arrays.stream(values())
                .filter(format -> code.equals(format.getCode()))
                .findAny()
                .orElse(null);
    }
}
