package ru.srfholding.task.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

import static ru.srfholding.common.constants.SchemaNames.TASK_SERVICE_SCHEMA;

/**
 * Логирование времени
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "time_log", schema = TASK_SERVICE_SCHEMA)
@EntityListeners(AuditingEntityListener.class)
public class TimeLogEntity {
    /**
     * ID лога
     */
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "log_id", nullable = false, insertable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private UUID logId;
    /**
     * ID задачи
     */
    @Column(name = "task_id", insertable = false, updatable = false)
    private UUID taskId;
    /**
     * Задача
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", referencedColumnName = "task_id")
    private TaskEntity task;
    /**
     * ID исполнителя
     */
    @Column(name = "user_id", nullable = false)
    private UUID assigneeId;
    /**
     * Часов заллогировано
     */
    @Column(name = "hours_logged", nullable = false)
    private Double hoursLogged;
    /**
     * Доп. описание
     */
    @Column(name = "description")
    private String description;
    /**
     * Дата работы
     */
    @Column(name = "work_date")
    @CreatedDate
    private LocalDate workDate;
    /**
     * Дата создания
     */
    @Column(name = "created_at")
    @CreatedDate
    private OffsetDateTime createdAt;
    /**
     * Кем создано
     */
    @Column(name = "created_by")
    @CreatedBy
    private UUID createdBy;
}
