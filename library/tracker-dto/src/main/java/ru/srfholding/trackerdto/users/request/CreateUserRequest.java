package ru.srfholding.trackerdto.users.request;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.srfholding.trackermodels.converter.constant.UserRole;

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
     * Имя пользователя
     */
    @NotBlank(message = "Имя не может быть пустым")
    String firstName;
    /**
     * Фамилия
     */
    @NotNull(message = "Фамилия должна быть заполнена!")
    String surname;
    /**
     * Отчество
     */
    String middleName;
    /**
     * Пароль
     */
    @NotNull(message = "Пароль должен быть заполнен")
    @Size(min = 12, max = 30, message = "Пароль не должен быть короче 12 символов")
    String password;
    /**
     * Роль
     */
    UserRole role;
}
