package ru.srfholding.task.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

import static ru.srfholding.common.constants.SchemaNames.TASK_SERVICE_SCHEMA;

/**
 * История изменеия задачи
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "task_history", schema = TASK_SERVICE_SCHEMA)
@EntityListeners(AuditingEntityListener.class)
public class TaskHistoryEntity {
    /**
     * Составной первичный ключ истории изменений задачи
     */
    @EmbeddedId
    private TaskHistoryKey taskHistoryKey;
    /**
     * ID задачи
     */
    @Column(name = "task_id", nullable = false)
    private UUID taskId;
    /**
     * Название поля
     */
    @Column(name = "field_name", nullable = false)
    private String fieldName;
    /**
     * Старое значение
     */
    @Column(name = "old_value")
    private String oldValue;
    /**
     * Новое значение
     */
    @Column(name = "new_value")
    private String newValue;
    /**
     * Тип изменения
     */
    @Column(name = "change_type")
    private String changeType;
    /**
     * Кем изменено
     */
    @CreatedBy
    @Column(name = "changed_by")
    private UUID changedBy;

}
