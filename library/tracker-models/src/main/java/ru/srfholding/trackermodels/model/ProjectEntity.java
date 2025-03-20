package ru.srfholding.trackermodels.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static lombok.AccessLevel.NONE;

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
    @Setter(NONE)
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
     * Владелец проекта
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", referencedColumnName = "user_id")
    @ToString.Exclude
    private UserEntity owner;
    /**
     * Дата создания
     */
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    /**
     * Дата обновления
     */
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    /**
     * Список задач проекта
     */
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<TaskEntity> tasks = new ArrayList<>();
}
