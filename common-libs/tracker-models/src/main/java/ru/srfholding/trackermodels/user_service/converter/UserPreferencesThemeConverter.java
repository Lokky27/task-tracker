package ru.srfholding.trackermodels.user_service.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;
import ru.srfholding.trackermodels.user_service.constant.UserPreferencesThemes;
import ru.srfholding.trackermodels.exception.TeamMemberRoleNotFoundException;

import static java.lang.String.format;

/**
 * Конвертер настройки темы
 */
@Slf4j
@Converter
public class UserPreferencesThemeConverter implements AttributeConverter<UserPreferencesThemes, Integer> {
    @Override
    public Integer convertToDatabaseColumn(UserPreferencesThemes attribute) {
        if (attribute == null) {
            throw new IllegalArgumentException(format("Недопустимое значение для конртирования значения роли в код [%s]", null));
        }
        return attribute.getCode();
    }

    @Override
    public UserPreferencesThemes convertToEntityAttribute(Integer dbData) {
        try {
            return UserPreferencesThemes.getThemeCode(dbData);
        } catch (TeamMemberRoleNotFoundException e) {
            log.warn("Значение роли по коду: [{}] не найдено", dbData, e);
            throw new TeamMemberRoleNotFoundException(String.format("Значение роли по коду: [%d] не найдено", dbData));
        }
    }
}
