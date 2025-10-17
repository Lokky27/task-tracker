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
 * Тэги для задач
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "task_tag", schema = TASK_SERVICE_SCHEMA)
@EntityListeners(AuditingEntityListener.class)
public class TaskTagEntity {
    /**
     * Составной ключ тэга
     */
    @EmbeddedId
    private TaskTagKey taskTagId;
    /**
     * Задача
     */
    @ManyToOne(fetch = LAZY)
    @MapsId("taskId")
    @JoinColumn(name = "task_id", referencedColumnName = "task_id")
    private TaskEntity taskEntity;
    /**
     * Цвет тэга
     */
    @Column(name = "tag_color", length = 7)
    private String color;
    /**
     * Дата добавления
     */
    @Column(name = "assigned_at")
    @CreatedDate
    private OffsetDateTime assignedAt;
    /**
     * Кем добавлен
     */
    @Column(name = "assigned_by")
    @CreatedBy
    private UUID assignedBy;

}
