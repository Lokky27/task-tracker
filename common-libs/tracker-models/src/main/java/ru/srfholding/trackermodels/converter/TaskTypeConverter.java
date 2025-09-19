package ru.srfholding.trackermodels.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ru.srfholding.trackermodels.converter.constant.TaskType;

import static ru.srfholding.trackermodels.converter.constant.TaskType.findTaskTypeByCode;

/**
 * Конвертер для типа задач
 */
@Converter
public class TaskTypeConverter implements AttributeConverter<TaskType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(TaskType attribute) {
        if (attribute == null) {
            throw new IllegalArgumentException("Тип задачи не может быть пустым");
        }
        return attribute.getCode();
    }

    @Override
    public TaskType convertToEntityAttribute(Integer code) {
        TaskType taskTypeByCode = findTaskTypeByCode(code);
        if (taskTypeByCode == null) {
            throw new IllegalArgumentException(String.format("Тип задачи с кодом %d не существует в системе", code));
        }
        return taskTypeByCode;
    }
}
