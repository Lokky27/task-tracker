package ru.srfholding.trackerdto.base.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
@Builder
public class TrackerResponse<T> {
    /**
     * Статус выполнения запроса
     */
    Status status;
    /**
     * Ошибки
     */
    @Builder.Default
    List<ResponseError> errors = new ArrayList<>();
    /**
     * Тело ответа
     */
    T result;
    /**
     * Метадата
     */
    ResponseMetadata metadata;

}
