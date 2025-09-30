package ru.srfholding.trackermodels.task_service.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ru.srfholding.trackermodels.task_service.constant.TaskStatusType;

import static ru.srfholding.trackermodels.task_service.constant.TaskStatusType.findStatusByCode;

/**
 * Конвертер статуса задач
 */
@Converter
public class TaskStatusConverter implements AttributeConverter<TaskStatusType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(TaskStatusType attribute) {
        if (attribute == null) {
            throw new IllegalArgumentException("Статус задачи не может быть пустым!");
        }

        return attribute.getCode();
    }

    @Override
    public TaskStatusType convertToEntityAttribute(Integer code) {
        TaskStatusType statusByCode = findStatusByCode(code);
        if (statusByCode == null) {
            throw new IllegalArgumentException(String.format("По коду %d статус не существует в системе", code));
        }

        return statusByCode;
    }
}
