package ru.srfholding.trackermodels.project_service.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;
import ru.srfholding.trackermodels.exception.AttributeCodeIsNullException;
import ru.srfholding.trackermodels.exception.AttributeNotFoundByCodeException;
import ru.srfholding.trackermodels.project_service.constant.ProjectStatus;

import static java.lang.String.format;
import static ru.srfholding.trackermodels.project_service.constant.ProjectStatus.findStatusByCode;

/**
 * Конвертер статусов проектов
 */
@Slf4j
@Converter
public class ProjectStatusConverter implements AttributeConverter<ProjectStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(ProjectStatus attribute) {
        if (attribute == null) {
            log.warn("Атрибут: [{}] не может быть пустым", ProjectStatus.class.getName());
            throw new AttributeCodeIsNullException(format("Атрибут: [%s] не может быть пустым", ProjectStatus.class.getName()));
        }

        return attribute.getCode();
    }

    @Override
    public ProjectStatus convertToEntityAttribute(Integer code) {
        if (code == null) {
            log.warn("Код атрибута пуст! [{}]", ProjectStatus.class.getName());
            throw new AttributeCodeIsNullException("Значения кода статуса не может быть пустым!");
        }
        ProjectStatus projectStatus = findStatusByCode(code);
        if (projectStatus == null) {
            log.warn("Статус проекта по коду [{}] в системе не найден", code);
            throw new AttributeNotFoundByCodeException(format("Статус проекта по коду [%d] в системе не найден", code));
        }

        return projectStatus;
    }
}
