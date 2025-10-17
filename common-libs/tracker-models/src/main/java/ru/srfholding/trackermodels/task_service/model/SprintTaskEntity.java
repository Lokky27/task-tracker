package ru.srfholding.trackermodels.task_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;
import java.util.UUID;

import static jakarta.persistence.FetchType.LAZY;
import static ru.srfholding.trackermodels.common_constants.SchemaNames.TASK_SERVICE_SCHEMA;

/**
 * Задачи в спринтах
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sprint_task", schema = TASK_SERVICE_SCHEMA)
@EntityListeners(AuditingEntityListener.class)
public class SprintTaskEntity {
    /**
     * Составной первичный ключ
     * sprint_id - ID спринта
     * task_id - ID задачи
     */
    @EmbeddedId
    private SprintTaskKey sprintTaskKey;
    /**
     * Спринт
     */
    @ManyToOne(fetch = LAZY)
    @MapsId("sprintId")
    @JoinColumn(name = "sprint_id", referencedColumnName = "sprint_id")
    private SprintEntity sprint;
    /**
     * Задача
     */
    @ManyToOne(fetch = LAZY)
    @MapsId("taskId")
    @JoinColumn(name = "task_id", referencedColumnName = "task_id")
    private TaskEntity taskEntity;
    /**
     * Дата добавления задачи в спринт
     */
    @Column(name = "added_at")
    @CreatedDate
    private OffsetDateTime addedAt;
    /**
     * Кем добавлена
     */
    @Column(name = "added_by")
    @CreatedBy
    private UUID addedBy;
    /**
     * Дата удаления задачи из спринта
     */
    @Column(name = "removed_at")
    private OffsetDateTime removedAt;
    /**
     * Кем удалено
     */
    @Column(name = "removed_by")
    private UUID removedBy;
}
