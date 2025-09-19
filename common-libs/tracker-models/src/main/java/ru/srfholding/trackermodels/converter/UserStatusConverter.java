package ru.srfholding.trackermodels.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ru.srfholding.trackermodels.converter.constant.UserStatus;

import static ru.srfholding.trackermodels.converter.constant.UserStatus.getRoleByCode;


/**
 * Конвертер для ролей пользователя
 */
@Converter
public class UserStatusConverter implements AttributeConverter<UserStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(UserStatus role) {
        if (role == null) {
            throw new IllegalArgumentException("Роль не может быть null");
        }

        return role.getCode();
    }

    @Override
    public UserStatus convertToEntityAttribute(Integer code) {
        if (code == null) {
            throw new IllegalArgumentException("Код роли не может быть null");
        }

        return getRoleByCode(code);
    }
}
