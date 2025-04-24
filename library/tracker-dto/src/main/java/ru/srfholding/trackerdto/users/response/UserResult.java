package ru.srfholding.trackerdto.users.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.srfholding.trackermodels.converter.constant.UserRole;

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
     * Имя пользователя
     */
    String firstName;
    /**
     * Фамилия
     */
    String surname;
    /**
     * Отчества
     */
    String middleName;
    /**
     * Роль
     */
    UserRole role;
    /**
     * Активен да/нет
     */
    Boolean isActive;
    /**
     * Дата создания
     */
    LocalDateTime createdAt;
}
