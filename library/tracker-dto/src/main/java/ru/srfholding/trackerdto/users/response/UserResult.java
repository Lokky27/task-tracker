package ru.srfholding.trackerdto.users.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.srfholding.trackermodels.converter.constant.UserStatus;

import java.time.LocalDateTime;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

/**
 * Тело ответа сервиса потзователей
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class UserResult {
    /**
     * ID пользователя
     */
    UUID userId;
    /**
     * Email пользователя
     */
    String email;
    /**
     * Логин пользователя
     */
    String login;
    /**
     * URI аватара
     */
    String avatarUri;
    /**
     * Таймзона
     */
    String timezone;
    /**
     * Язык
     */
    String language;
    /**
     * Статус
     */
    UserStatus status;
    /**
     * Дата создания
     */
    LocalDateTime createdAt;
    /**
     * Дата обновления
     */
    LocalDateTime updatedAt;
}
