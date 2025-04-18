package ru.srfholding.trackermodels.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ru.srfholding.trackermodels.converter.constant.PriorityType;

import static ru.srfholding.trackermodels.converter.constant.PriorityType.findPriorityByCode;

/**
 * Конвертер приоритетов
 */
@Converter
public class PriorityConverter implements AttributeConverter<PriorityType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(PriorityType priorityType) {
        if (priorityType == null) {
            throw new IllegalArgumentException("Приоритет не соответствует приоритетам системы");
        }

        return priorityType.getCode();
    }

    @Override
    public PriorityType convertToEntityAttribute(Integer code) {
        PriorityType priorityByCode = findPriorityByCode(code);
        if (priorityByCode == null) {
            throw new IllegalArgumentException(String.format("По коду %d не найдено соответствий по приоритету", code));
        }

        return priorityByCode;
    }
}
