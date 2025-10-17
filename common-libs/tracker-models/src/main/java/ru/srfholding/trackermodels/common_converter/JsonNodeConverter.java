package ru.srfholding.trackermodels.common_converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;
import ru.srfholding.trackermodels.exception.JsonConvertingException;

import static java.lang.String.format;

/**
 * Конвертер для JsonB полей
 */
@Slf4j
@Converter
public class JsonNodeConverter implements AttributeConverter<JsonNode, String> {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    @Override
    public String convertToDatabaseColumn(JsonNode attribute) {
        if (attribute == null) {
            log.warn("Значение jsonb из БД пустое!");
            return null;
        }

        try {
            return OBJECT_MAPPER.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            log.error("Ошибка сериализации занчения из БД в строку", e);
            throw new RuntimeException("Ошибка сериализации занчения из БД в строку");
        }
    }

    @Override
    public JsonNode convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            log.warn("Пустое значение для сериализации в JSON");
            return null;
        }

        try {
            return OBJECT_MAPPER.readTree(dbData);
        } catch (JsonProcessingException e) {
            log.error("Ошибка сериализации в JSON строки: [{}]", dbData, e);
            throw new JsonConvertingException(format("Оишбка сериализации в JSON строки: [%s]", dbData));
        }
    }
}
