package ru.srfholding.trackermodels.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static lombok.AccessLevel.NONE;

/**
 * Сущность задачи
 */
@Entity
@Table(name = "task")
@Getter
@Setter
public class TaskEntity {
    /**
     * ID задачи
     */
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "task_id", updatable = false, nullable = false)
    @Setter(NONE)
    private UUID taskId;
    /**
     * Заголовок задачи
     */
    @Column(name = "title", nullable = false)
    private String title;
    /**
     * Описание задачи
     */
    @Column(name = "description")
    private String description;
    /**
     * Дедлайн
     */
    @Column(name = "deadline")
    private LocalDate deadline;
    /**
     * Статус задачи
     */
    @Column(name = "status_code")
    private Integer statusCode;
    /**
     * Проект
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", referencedColumnName = "project_id")
    private ProjectEntity project;
    /**
     * Исполнитель
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id", referencedColumnName = "user_id")
    private UserEntity assignee;
    /**
     * Создатель
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", referencedColumnName = "user_id")
    private UserEntity createdBy;
    /**
     * Дата создания
     */
    @CreationTimestamp
    @Column(name = "created_at")
    private OffsetDateTime createdAt;
    /**
     * Обновил
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by")
    private UserEntity updatedBy;
    /**
     * Дата обновления
     */
    @UpdateTimestamp
    @Column(name = "updated_at")
    @Setter(NONE)
    private OffsetDateTime updatedAt;
    /**
     * ID задачи родителя
     */
    @Column(name = "parent_task_id", insertable = false, updatable = false)
    private UUID parentTaskId;
    /**
     * Родительская задача
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_task_id")
    private TaskEntity parentTask;
    /**
     * Подзадачи
     */
    @OneToMany(
            mappedBy = "parentTask",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<TaskEntity> subTasks = new ArrayList<>();
    /**
     * Тип задачи
     */
    @Column(name = "task_type_code")
    private Integer taskTypeCode;
}
