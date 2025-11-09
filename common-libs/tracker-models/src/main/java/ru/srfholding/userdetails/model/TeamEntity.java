package ru.srfholding.userdetails.model;

import jakarta.persistence.*;
import lombok.*;
import ru.srfholding.common.base.BaseEntity;

import java.time.LocalDateTime;
import java.util.UUID;

import static ru.srfholding.common.constants.SchemaNames.USER_DETAILS_SERVICE_SCHEMA;

/**
 * Сущность команды
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "team", schema = USER_DETAILS_SERVICE_SCHEMA)
public class TeamEntity extends BaseEntity {
    /**
     * ID команды
     */
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "team_id", nullable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private UUID teamId;
    /**
     * Название команды
     */
    @Column(name = "team_name", nullable = false)
    private String teamName;
    /**
     * Описание команды
     */
    @Column(name = "description")
    private String description;
    /**
     * Максимальная численность
     */
    @Column(name = "max_members")
    private Integer maxMembers;
    /**
     * Активность команды (Да/Нет)
     */
    @Column(name = "is_active")
    private Boolean isActive;
    /**
     * Дата удаления
     */
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
