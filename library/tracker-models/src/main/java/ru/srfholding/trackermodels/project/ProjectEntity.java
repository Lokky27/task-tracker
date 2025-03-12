package ru.srfholding.trackermodels.project;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Сущность Проекта
 */
@Getter
@Setter
@Entity
@Table(name = "project")
public class ProjectEntity {
    /**
     * ID проекта
     */
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "project_id", updatable = false, nullable = false)
    private UUID projectId;
    /**
     * Название проекта
     */
    @Column(name = "name", nullable = false)
    private String projectName;
    /**
     * Описание проекта
     */
    private String description;
    /**
     * Статус проекта
     */
    @Column(name = "status_code")
    private Integer statusCode;
    /**
     * ID владельца проекта
     */
    @Column(name = "owner_id", insertable = false, updatable = false)
    private UUID ownerId;
    /**
     * Дата создания
     */
    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;
    /**
     * Дата обновления
     */
    @Column(name = "updated_at", insertable = false, updatable = false)
    private LocalDateTime updatedAt;
}
