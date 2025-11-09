package ru.srfholding.project.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;
import ru.srfholding.project.constant.ProjectStatus;
import ru.srfholding.common.exception.AttributeCodeIsNullException;
import ru.srfholding.common.exception.AttributeNotFoundByCodeException;

import static java.lang.String.format;
import static ru.srfholding.project.constant.ProjectStatus.findStatusByCode;

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
