package ru.srfholding.project.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;
import ru.srfholding.project.constant.ProjectMemberRole;
import ru.srfholding.common.exception.AttributeNotFoundByCodeException;

import static java.lang.String.format;

/**
 * Конвертер кода роли члена проекта
 */
@Slf4j
@Converter
public class ProjectMemberRoleConverter implements AttributeConverter<ProjectMemberRole, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ProjectMemberRole attribute) {
        if (attribute == null) {
            log.warn("Некорректное значение атрибута: [{}]", ProjectMemberRole.class.getName());
            throw new IllegalArgumentException(format("Некорректное значение атрибут: [%s]", ProjectMemberRole.class.getName()));
        }

        return attribute.getCode();
    }

    @Override
    public ProjectMemberRole convertToEntityAttribute(Integer dbData) {
        ProjectMemberRole projectMemberRole = ProjectMemberRole.getRoleByCode(dbData);
        if (projectMemberRole == null) {
            log.warn("Значение атрибута по коду [{}] не найдено в системе!", dbData);
            throw new AttributeNotFoundByCodeException(format("Значение атрибута по коду [%d]  системе не найдено!", dbData));
        }

        return projectMemberRole;
    }
}
