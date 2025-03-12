package ru.srfholding.trackermodels.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Сущность пользователя
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class UserEntity {
    /**
     * ID пользователя
     */
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "user_id", updatable = false, nullable = false)
    private UUID userId;
    /**
     * Email пользователя
     */
    @Column(name = "email", nullable = false)
    private String email;
    /**
     * Имя пользователя
     */
    @Column(name = "first_name")
    private String firstName;
    /**
     * Фамилия пользователя
     */
    @Column(name = "surname")
    private String surname;
    /**
     * Отчество пользователя
     */
    @Column(name = "middle_name")
    private String middleName;
    /**
     * Пароль
     */
    @Column(name = "password")
    private char[] password;
    /**
     * Роль
     */
    @Column(name = "role")
    private Integer role;
    /**
     * Пользователь активен/не активен
     */
    @Column(name = "is_active")
    private Boolean isActive;
    /**
     * Дата создания пользователя
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    /**
     * Дата обновления пользователя
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
