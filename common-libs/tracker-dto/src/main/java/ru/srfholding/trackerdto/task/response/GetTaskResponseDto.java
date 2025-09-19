package ru.srfholding.trackerdto.task.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.srfholding.trackerdto.base.response.ResponseError;
import ru.srfholding.trackerdto.base.response.Status;

import java.util.ArrayList;
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
    Status status;
    /**
     * Результат
     */
    TaskResult taskResult;
    /**
     * Ошибки
     */
    List<ResponseError> errors = new ArrayList<>();
}
