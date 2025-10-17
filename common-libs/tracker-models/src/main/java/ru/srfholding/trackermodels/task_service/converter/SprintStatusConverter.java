package ru.srfholding.trackermodels.task_service.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;
import ru.srfholding.trackermodels.exception.SprintStatusNotFoundException;
import ru.srfholding.trackermodels.task_service.constant.SprintStatus;

import static java.lang.String.format;

/**
 * Конвертер статуса спринта
 */
@Slf4j
@Converter
public class SprintStatusConverter implements AttributeConverter<SprintStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(SprintStatus attribute) {
        if (attribute == null) {
            log.warn("Статус спринта не может быть пустым!");
            throw new IllegalArgumentException("Статус спринта не может быть пустым");
        }

        return attribute.getCode();
    }

    @Override
    public SprintStatus convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            log.warn("Код статуса спринта не может быть пустым!");
            throw new IllegalArgumentException("Код статуса спринта не может быть пустым!");
        }
        SprintStatus springStatusCode = SprintStatus.getByCode(dbData);
        if (springStatusCode == null) {
            log.warn("Статус спринта не найден по коду: [{}]", dbData);
            throw new SprintStatusNotFoundException(format("Статус спринта не найден по коду [%d]", dbData));
        }
        return null;
    }
}
