package ru.srfholding.trackerdto.base.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.OffsetDateTime;

import static lombok.AccessLevel.PRIVATE;

/**
 * Метадата для ответа
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class ResponseMetadata {
    /**
     * ID запроса
     */
    String rqUid;
    /**
     * Временная метка
     */
    OffsetDateTime rqTm;
}
