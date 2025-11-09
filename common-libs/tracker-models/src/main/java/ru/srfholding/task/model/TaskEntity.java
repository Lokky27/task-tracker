package ru.srfholding.task.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import ru.srfholding.common.base.BaseEntity;
import ru.srfholding.task.constant.TaskPriorityType;
import ru.srfholding.task.constant.TaskStatusType;
import ru.srfholding.task.constant.TaskType;
import ru.srfholding.task.converter.TaskPriorityConverter;
import ru.srfholding.task.converter.TaskStatusConverter;
import ru.srfholding.task.converter.TaskTypeConverter;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static ru.srfholding.common.constants.SchemaNames.TASK_SERVICE_SCHEMA;

/**
 * Сущность задачи
 */
@Entity
@Table(name = "task", schema = TASK_SERVICE_SCHEMA)
@Getter
@Setter
public class TaskEntity extends BaseEntity {
    /**
     * ID задачи
     */
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "task_id", updatable = false, nullable = false)
    @Setter(AccessLevel.NONE)
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
     * ID проекта
     */
    @Column(name = "project_id", nullable = false)
    private UUID projectId;
    /**
     * ID исполнителя
     */
    @Column(name = "assignee_id")
    private UUID assigneeId;
    /**
     * ID автора
     */
    @Column(name = "reporter_id", nullable = false)
    private UUID reporterId;
    /**
     * Статус задачи
     */
    @Column(name = "task_status_code")
    @Convert(converter = TaskStatusConverter.class)
    private TaskStatusType statusCode;
    /**
     * Тип задачи
     */
    @Column(name = "task_type_code", nullable = false)
    @Convert(converter = TaskTypeConverter.class)
    private TaskType taskTypeCode;
    /**
     * Приоритет
     */
    @Column(name = "task_priority_code")
    @Convert(converter = TaskPriorityConverter.class)
    private TaskPriorityType priority;
    /**
     * ID родительской задачи
     */
    @Column(name = "parent_task_id", updatable = false, insertable = false)
    private UUID parentTaskId;
    /**
     * Родительская задача
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_task_id", referencedColumnName = "task_id")
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
     * Уровень иерархии
     */
    @Column(name = "hierarchy_level")
    private Integer hierarchyLevel;
    /**
     * Дедлайн
     */
    @Column(name = "due_date")
    private OffsetDateTime dueDate;
    /**
     * Дата начала
     */
    @Column(name = "start_date")
    private OffsetDateTime startDate;
    /**
     * Дата завершения
     */
    @Column(name = "completed_at")
    private OffsetDateTime completedAt;
    /**
     * Оценка в стори-поинтах
     */
    @Column(name = "story_points")
    private Integer storyPoints;
    /**
     * Оценка в часах
     */
    @Column(name = "estimated_hours")
    private Double estimatedHours;
    /**
     * Потрачено часов
     */
    @Column(name = "actual_hours")
    private Double actualHours;
    /**
     * Решение
     */
    @Column(name = "resolution")
    private String resolution;
    /**
     * Дата удаления
     */
    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;

}
