package ru.srfholding.trackerdto.task.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.srfholding.trackermodels.converter.constant.StatusType;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

/**
 * Ответ на смену статуса
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class ChangeStatusTaskResponse {
    /**
     * Статус запроса
     */
    StatusType statusCode;
    /**
     * Ответ успешен (да/нет)
     */
    Boolean success;
    /**
     * Тело ответа
     */
    ChangeStatusTaskBody result;
    /**
     * Ошибки
     */
    List<ResponseError> errors;

}
