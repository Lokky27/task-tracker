package ru.srfholding.project.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.srfholding.project.constant.ProjectPriority;
import ru.srfholding.common.exception.AttributeCodeIsNullException;
import ru.srfholding.common.exception.AttributeNotFoundByCodeException;

import static java.lang.String.format;

/**
 * Конверер приоритета проекта
 */
@Getter
@Slf4j
@Converter
public class ProjectPriorityConverter implements AttributeConverter<ProjectPriority, Integer> {
    @Override
    public Integer convertToDatabaseColumn(ProjectPriority attribute) {
        if (attribute == null) {
            log.warn("Атрибут [{}] не может быть пустым", ProjectPriority.class.getName());
            throw new IllegalArgumentException("Атрибут не может быть пустым: " + ProjectPriority.class.getName());
        }

        return attribute.getCode();
    }

    @Override
    public ProjectPriority convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            log.warn("Код атрибута [{}] не может быть пустым", ProjectPriority.class.getName());
            throw new AttributeCodeIsNullException(format("Код атрибута [%s] не может быть пустым", ProjectPriority.class.getName()));
        }
        ProjectPriority priority = ProjectPriority.getByCode(dbData);
        if (priority == null) {
            log.warn("Приоритет по коду: [{}] в системе не найден", dbData);
            throw new AttributeNotFoundByCodeException(format("Приоритет по коду [%d] не найден", dbData));
        }
        return priority;
    }
}
