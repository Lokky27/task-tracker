package ru.srfholding.trackermodels.task;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.srfholding.trackermodels.project.ProjectEntity;
import ru.srfholding.trackermodels.user.UserEntity;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

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
     * ID проекта
     */
    @Column(name = "project_id", insertable = false, updatable = false)
    private UUID projectId;
    /**
     * Проект
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private ProjectEntity project;
    /**
     * ID исполнителя
     */
    @Column(name = "assignee_id", insertable = false, updatable = false)
    private UUID assigneeId;
    /**Исполнитель*/
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity assignee;
    /**
     * ID создателя
     */
    @Column(name = "created_by", insertable = false, updatable = false)
    private UUID createdBy;
    /**
     * Создатель
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity creator;
    /**
     * Дата создания
     */
    @Column(name = "created_at")
    private OffsetDateTime createdAt;
    /**
     * ID пользователя, который обновил
     */
    @Column(name = "updated_by")
    private UUID updatedBy;
    /**
     * Обновил
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity updater;
    /**
     * Дата обновления
     */
    @Column(name = "updated_at")
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
    private TaskEntity parentTask;
    /**
     * Подзадачи
     */
    @OneToMany(
            mappedBy = "parentTask",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<TaskEntity> subTasks;
    /**
     * Тип задачи
     */
    @Column(name = "task_type_code")
    private Integer taskTypeCode;
}
