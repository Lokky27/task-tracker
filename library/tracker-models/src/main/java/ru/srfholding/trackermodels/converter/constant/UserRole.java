package ru.srfholding.trackermodels.converter.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * Роль пользователя
 */
@Getter
@RequiredArgsConstructor
public enum UserRole {
    USER(1, "Пользователь"),
    ADMIN(2, "Администратор");

    private final Integer code;
    private final String description;

    public static UserRole getRoleByCode(Integer code) {
        return Arrays.stream(values())
                .filter(role -> code.equals(role.getCode()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Роль с кодом " + code + " в системе не найдена"));
    }
}
