package ru.srfholding.trackermodels.task;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private String title;
    /**
     * Описание задачи
     */
    private String description;
    /**
     * Дедлайн
     */
    private LocalDate deadline;
    /**
     * Статус задачи
     */
    private Integer statusCode;
    /**
     * ID проекта
     */
    private UUID projectId;
    /**
     * ID исполнителя
     */
    private UUID assigneeId;
    /**
     * ID создателя
     */
    private UUID createdBy;
    /**
     * Дата создания
     */
    private LocalDateTime createdAt;
    /**
     * ID пользователя, который обновил
     */
    private UUID updatedBy;
    /**
     * Дата обновления
     */
    private LocalDateTime updatedAt;
}
