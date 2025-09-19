package ru.srfholding.trackermodels.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.srfholding.trackermodels.converter.UserStatusConverter;
import ru.srfholding.trackermodels.converter.constant.UserStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static lombok.AccessLevel.NONE;

/**
 * Сущность пользователя
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_profile", schema = "task_tracker")
public class UserEntity {
    /**
     * ID пользователя
     */
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "user_id", updatable = false, nullable = false)
    @Setter(NONE)
    private UUID userId;
    /**
     * Email пользователя
     */
    @Column(name = "email", nullable = false)
    private String email;
    /**
     * ID из Keycloak
     */
    @Column(name = "keycloak_id")
    private UUID keycloakId;
    /**
     * Логин пользователя
     */
    @Column(name = "display_name")
    private String displayName;
    /**
     * Статус пользователя
     */
    @Column(name = "status")
    @Convert(converter = UserStatusConverter.class)
    private UserStatus status;
    /**
     * URI Аватара
     */
    @Column(name = "avatar_uri")
    private String avatarUri;
    /**
     * Таймзона
     */
    @Column(name = "timezone")
    private String timezone;
    /**
     * Язык
     */
    @Column(name = "language")
    private String language;
    /**
     * Дата создания пользователя
     */
    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;
    /**
     * Дата обновления пользователя
     */
    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    /**
     * Задачи пользователя
     */
    @OneToMany(
            mappedBy = "assignee",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<TaskEntity> tasks = new ArrayList<>();

}
