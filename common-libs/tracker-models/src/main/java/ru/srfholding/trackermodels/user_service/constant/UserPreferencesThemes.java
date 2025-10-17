package ru.srfholding.trackermodels.user_service.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.srfholding.trackermodels.exception.UserPreferencesThemeNotFoundException;

import java.util.Arrays;

@Slf4j
@Getter
@RequiredArgsConstructor
public enum UserPreferencesThemes {
    SYSTEM(1, "Системная"),
    DARK(2, "Темная"),
    LIGHT(3, "Светлая");

    private final Integer code;
    private final String description;

    public static UserPreferencesThemes getThemeCode(Integer themeCode) {
        return Arrays.stream(UserPreferencesThemes.values())
                .filter(theme -> themeCode.equals(theme.code))
                .findAny()
                .orElseThrow(() -> {
                    log.warn("Не найдено темы по коду [{}]", themeCode);
                    return new UserPreferencesThemeNotFoundException(String.format("Не найдено темы по коду [%d]", themeCode));
                });
    }
}
