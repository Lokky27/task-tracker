package ru.srfholding.task.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ru.srfholding.task.constant.TaskPriorityType;

import static ru.srfholding.task.constant.TaskPriorityType.findPriorityByCode;

/**
 * Конвертер приоритетов
 */
@Converter
public class TaskPriorityConverter implements AttributeConverter<TaskPriorityType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(TaskPriorityType taskPriorityType) {
        if (taskPriorityType == null) {
            throw new IllegalArgumentException("Приоритет не соответствует приоритетам системы");
        }

        return taskPriorityType.getCode();
    }

    @Override
    public TaskPriorityType convertToEntityAttribute(Integer code) {
        TaskPriorityType priorityByCode = findPriorityByCode(code);
        if (priorityByCode == null) {
            throw new IllegalArgumentException(String.format("По коду %d не найдено соответствий по приоритету", code));
        }

        return priorityByCode;
    }
}
