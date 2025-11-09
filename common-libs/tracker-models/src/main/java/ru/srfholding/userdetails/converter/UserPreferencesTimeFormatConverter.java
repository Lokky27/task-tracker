package ru.srfholding.userdetails.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;
import ru.srfholding.common.exception.UserPreferencesTimeFormatNotFoundException;
import ru.srfholding.userdetails.constant.UserPreferencesTimeFormats;

/**
 * Конвертер типа времени
 */
@Slf4j
@Converter
public class UserPreferencesTimeFormatConverter implements AttributeConverter<UserPreferencesTimeFormats, Integer> {
    @Override
    public Integer convertToDatabaseColumn(UserPreferencesTimeFormats attribute) {
        if (attribute == null) {
            log.warn("Недопустимое значение формата времени");
            throw new IllegalArgumentException("Недопустимое значение формата времени");
        }

        return attribute.getCode();
    }

    @Override
    public UserPreferencesTimeFormats convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            log.warn("Код настройки формата времени не может быть пустым");
            throw new IllegalArgumentException("Код настройки формата времени не может быть пустым");
        }
        UserPreferencesTimeFormats preferencesByCode = UserPreferencesTimeFormats.getPreferencesByCode(dbData);
        if (preferencesByCode == null) {
            log.warn("По коду [{}] настройка не найдена", dbData);
            throw new UserPreferencesTimeFormatNotFoundException(String.format("По коду [%s] настройка не найдена", dbData));
        }

        return preferencesByCode;
    }
}
