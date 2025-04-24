package ru.srfholding.trackermodels.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.srfholding.trackermodels.converter.UserRoleConverter;
import ru.srfholding.trackermodels.converter.constant.UserRole;

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
@Table(name = "users", schema = "task_tracker")
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
    @Convert(converter = UserRoleConverter.class)
    private UserRole role;
    /**
     * Пользователь активен/не активен
     */
    @Column(name = "is_active")
    private Boolean isActive;
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
