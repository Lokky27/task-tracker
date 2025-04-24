package ru.srfholding.trackermodels.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ru.srfholding.trackermodels.converter.constant.UserRole;

import static ru.srfholding.trackermodels.converter.constant.UserRole.getRoleByCode;

/**
 * Конвертер для ролей пользователя
 */
@Converter
public class UserRoleConverter implements AttributeConverter<UserRole, Integer> {
    @Override
    public Integer convertToDatabaseColumn(UserRole role) {
        if (role == null) {
            throw new IllegalArgumentException("Роль не может быть null");
        }

        return role.getCode();
    }

    @Override
    public UserRole convertToEntityAttribute(Integer code) {
        if (code == null) {
            throw new IllegalArgumentException("Код роли не может быть null");
        }

        return getRoleByCode(code);
    }
}
