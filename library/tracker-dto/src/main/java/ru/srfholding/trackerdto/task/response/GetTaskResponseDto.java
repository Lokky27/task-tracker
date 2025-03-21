package ru.srfholding.trackerdto.task.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

/**
 * Ответ на запрос получения задачи
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class GetTaskResponseDto {
    /**
     * Статус
     */
    Integer statusCode;
    /**
     * Ответ успешен да/нет
     */
    Boolean success;
    /**
     * Результат
     */
    Result result;
    /**
     * Ошибки
     */
    List<ResponseError> errors;
}
