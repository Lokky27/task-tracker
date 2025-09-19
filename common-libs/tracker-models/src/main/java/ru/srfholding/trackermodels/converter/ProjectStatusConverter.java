package ru.srfholding.trackermodels.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;
import ru.srfholding.trackermodels.converter.constant.ProjectStatus;

import static ru.srfholding.trackermodels.converter.constant.ProjectStatus.findStatusByCode;

/**
 * Конвертер статусов проектов
 */
@Slf4j
@Converter
public class ProjectStatusConverter implements AttributeConverter<ProjectStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(ProjectStatus attribute) {
        if (attribute == null) {
            log.error("Ошибка конвертирования атрибута");
            throw new IllegalArgumentException("Ошибка конвертирования атрибута");
        }

        return attribute.getCode();
    }

    @Override
    public ProjectStatus convertToEntityAttribute(Integer code) {
        if (code == null) {
            log.error("Значения кода статуса не может быть пустым!");
            throw new IllegalArgumentException("Значения кода статуса не может быть пустым!");
        }

        return findStatusByCode(code);
    }
}
