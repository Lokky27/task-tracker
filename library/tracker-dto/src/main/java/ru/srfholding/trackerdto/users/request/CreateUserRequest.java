package ru.srfholding.trackerdto.users.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

/**
 * Запрос на создание пользователя
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class CreateUserRequest {
    /**
     * Email пользователя
     */
    @NotEmpty(message = "Поле Email не может быть пустым!")
    @Email(message = "Email не валиден!", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    String email;
    /**
     * Пароль
     */
    @NotNull(message = "Пароль должен быть заполнен")
    @Size(min = 12, max = 30, message = "Пароль не должен быть короче 12 символов")
    String password;
}
