package ru.srfholding.trackermodels.user_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.srfholding.trackermodels.base.BaseEntity;

import java.time.LocalDateTime;
import java.util.UUID;

import static lombok.AccessLevel.NONE;
import static ru.srfholding.trackermodels.common_constants.SchemaNames.USER_DETAILS_SERVICE_SCHEMA;

/**
 * Сущность пользователя
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_profile", schema = USER_DETAILS_SERVICE_SCHEMA)
public class UserProfileEntity extends BaseEntity {
    /**
     * ID пользователя
     */
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "user_profile_id", updatable = false, nullable = false)
    @Setter(NONE)
    private UUID userProfileId;
    /**
     * Email пользователя
     */
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    /**
     * ID из Keycloak
     */
    @Column(name = "keycloak_id", nullable = false, unique = true)
    private UUID keycloakId;
    /**
     * Имя пользователя
     */
    @Column(name = "first_name", length = 50)
    private String firstName;
    /**
     * Фамилия пользователя
     */
    @Column(name = "last_name", length = 100)
    private String lastName;
    /**
     * Отчество
     */
    @Column(name = "middle_name", length = 100)
    private String middleName;
    /**
     * URL аватарки
     */
    @Column(name = "avatar_url", length = 500)
    private String avatarUrl;
    /**
     * Таймзона
     */
    @Column(name = "timezone", length = 50)
    private String timezone;
    /**
     * Язык пользователя
     */
    @Column(name = "language", length = 5)
    private String language;
    /**
     * Номер телефона
     */
    @Column(name = "phone", length = 20)
    private String phone;
    /**
     * Активен (Да/Нет)
     */
    @Column(name = "is_active")
    private Boolean isActive;
    /**
     * Адрес электронной почты активен (Да/Нет)
     */
    @Column(name = "is_email_verified")
    private Boolean isEmailVerified;
    /**
     * Дата последнего входа
     */
    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;
    /**
     * Дата удаления
     */
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
