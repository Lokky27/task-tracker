package ru.srfholding.trackerdto.base.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class ValidationErrorDto {
    /**
     * Название поля
     */
    String fieldName;
    /**
     * Сообщение об ошибке
     */
    String errorMessage;
}
