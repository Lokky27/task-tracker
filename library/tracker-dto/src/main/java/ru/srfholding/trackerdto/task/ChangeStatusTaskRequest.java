package ru.srfholding.trackerdto.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.srfholding.trackermodels.converter.constant.StatusType;

import static lombok.AccessLevel.PRIVATE;

/**
 * Запрос на смену статуса задачи
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class ChangeStatusTaskRequest {
    /**
     * Новый статус задачи
     */
    StatusType statusCode;
}
