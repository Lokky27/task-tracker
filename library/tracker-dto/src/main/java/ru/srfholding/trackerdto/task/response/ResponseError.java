package ru.srfholding.trackerdto.task.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

import static lombok.AccessLevel.PRIVATE;

/**
 * Ошибки ответа
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class ResponseError {
    /**
     * Код ошибки
     */
    String errorCode;
    /**
     * Описание ошибки
     */
    String errorMessage;
    /**
     * Тип ошибки
     */
    Integer errorType;
    /**
     * Время возникновения
     */
    Instant timestamp;
}
