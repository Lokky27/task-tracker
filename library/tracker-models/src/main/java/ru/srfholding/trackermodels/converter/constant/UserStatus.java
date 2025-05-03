package ru.srfholding.trackermodels.converter.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * Роль пользователя
 */
@Getter
@RequiredArgsConstructor
public enum UserStatus {
    ACTIVE(1, "Пользователь"),
    DEACTIVE(2, "Администратор");

    private final Integer code;
    private final String description;

    public static UserStatus getRoleByCode(Integer code) {
        return Arrays.stream(values())
                .filter(role -> code.equals(role.getCode()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Роль с кодом " + code + " в системе не найдена"));
    }
}
