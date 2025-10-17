package ru.srfholding.trackermodels.user_service.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;
import ru.srfholding.trackermodels.user_service.constant.TeamMemberRoles;
import ru.srfholding.trackermodels.exception.TeamMemberRoleNotFoundException;

import static java.lang.String.format;
import static ru.srfholding.trackermodels.user_service.constant.TeamMemberRoles.getCodeFromType;

/**
 * Конвертер для ролей пользователей в команде
 */
@Slf4j
@Converter
public class TeamMemberRoleConverter implements AttributeConverter<TeamMemberRoles, Integer> {
    @Override
    public Integer convertToDatabaseColumn(TeamMemberRoles attribute) {
        if (attribute == null) {
            throw new IllegalArgumentException(format("Недопустимое значение для конртирования значения роли в код [%s]", null));
        }

        return attribute.getCode();
    }

    @Override
    public TeamMemberRoles convertToEntityAttribute(Integer dbData) {
        try {
            return getCodeFromType(dbData);
        } catch (TeamMemberRoleNotFoundException e) {
            log.warn("Значение роли по коду: [{}] не найдено", dbData, e);
            throw new TeamMemberRoleNotFoundException(String.format("Значение роли по коду: [%d] не найдено", dbData));
        }
    }
}
