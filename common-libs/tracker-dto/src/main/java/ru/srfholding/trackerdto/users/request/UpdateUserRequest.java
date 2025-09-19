package ru.srfholding.trackerdto.users.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.srfholding.trackermodels.converter.constant.UserStatus;

import static lombok.AccessLevel.PRIVATE;

/**
 * Запрос на обновление пользователя
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class UpdateUserRequest {
    /**
     * Логин пользователя
     */
    String login;
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
    /**
     * Статус пользователя
     */
    UserStatus status;
}
