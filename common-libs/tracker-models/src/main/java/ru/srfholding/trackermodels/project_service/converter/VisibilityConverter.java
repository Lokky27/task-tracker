package ru.srfholding.trackermodels.project_service.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;
import ru.srfholding.trackermodels.exception.VisibilityCodeNotFoundException;
import ru.srfholding.trackermodels.project_service.constant.VisibilityCode;

import static java.lang.String.format;

@Slf4j
@Converter
public class VisibilityConverter implements AttributeConverter<VisibilityCode, Integer> {
    @Override
    public Integer convertToDatabaseColumn(VisibilityCode attribute) {
        if (attribute == null) {
            log.warn("Конвертируемый атрибут пуст!: [{}]", VisibilityCode.class.getName());
            throw new IllegalArgumentException("Конвертируемый атрибут пуст!");
        }

        return attribute.getCode();
    }

    @Override
    public VisibilityCode convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            log.warn("Код атрибута пуст! [{}]", VisibilityCode.class.getName());
            throw new IllegalArgumentException("Код атрибута не может быть пуст!");
        }
        VisibilityCode visibilityCode = VisibilityCode.getByCode(dbData);
        if (visibilityCode == null) {
            log.warn("Соответствующее коду [{}] значение не найдено!", dbData);
            throw new VisibilityCodeNotFoundException(format("Соответствующее коду [%s] значение не найдено!", dbData));
        }
        return visibilityCode;
    }
}
