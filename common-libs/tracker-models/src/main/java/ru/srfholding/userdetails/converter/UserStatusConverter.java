package ru.srfholding.userdetails.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ru.srfholding.userdetails.constant.UserStatus;


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

        return UserStatus.getRoleByCode(code);
    }
}
