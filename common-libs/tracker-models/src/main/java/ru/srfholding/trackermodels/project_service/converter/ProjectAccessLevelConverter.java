package ru.srfholding.trackermodels.project_service.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;
import ru.srfholding.trackermodels.exception.AttributeNotFoundByCodeException;
import ru.srfholding.trackermodels.project_service.constant.ProjectAccessLevel;

import static java.lang.String.format;

/**
 * Конвертер уровня доступа
 */
@Slf4j
@Converter
public class ProjectAccessLevelConverter implements AttributeConverter<ProjectAccessLevel, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ProjectAccessLevel attribute) {
        if (attribute == null) {
            log.warn("Значение атрибута: [{}] пустое", ProjectAccessLevel.class.getName());
            throw new IllegalArgumentException(format("Значение атрибута: [%s] пустое", ProjectAccessLevel.class.getName()));
        }

        return attribute.getCode();
    }

    @Override
    public ProjectAccessLevel convertToEntityAttribute(Integer dbData) {
        ProjectAccessLevel projectAccessLevel = ProjectAccessLevel.getByCode(dbData);
        if (projectAccessLevel == null) {
            log.warn("Значение атрибута по коду: [{}] не найдено в системе!", dbData);
            throw new AttributeNotFoundByCodeException(format("Значение атрибута по коду: [%d] не найдено в системе!", dbData));
        }

        return projectAccessLevel;
    }
}
