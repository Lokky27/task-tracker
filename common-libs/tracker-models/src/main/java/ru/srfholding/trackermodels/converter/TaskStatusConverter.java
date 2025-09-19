package ru.srfholding.trackermodels.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ru.srfholding.trackermodels.converter.constant.StatusType;

import static ru.srfholding.trackermodels.converter.constant.StatusType.findStatusByCode;

/**
 * Конвертер статуса задач
 */
@Converter
public class TaskStatusConverter implements AttributeConverter<StatusType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(StatusType attribute) {
        if (attribute == null) {
            throw new IllegalArgumentException("Статус задачи не может быть пустым!");
        }

        return attribute.getCode();
    }

    @Override
    public StatusType convertToEntityAttribute(Integer code) {
        StatusType statusByCode = findStatusByCode(code);
        if (statusByCode == null) {
            throw new IllegalArgumentException(String.format("По коду %d статус не существует в системе", code));
        }

        return statusByCode;
    }
}
