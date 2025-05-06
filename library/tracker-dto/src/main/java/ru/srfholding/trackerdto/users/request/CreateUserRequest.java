package ru.srfholding.trackerdto.users.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

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
     * Логин пользователя
     */
    String login;
    /**
     * ID системы Keycloak
     */
    UUID keycloakId;
    /**
     * URL аватара
     */
    String avatarUrl;
    /**
     * Таймзона
     */
    String timezone;
    /**
     * Язык
     */
    String language;
}
