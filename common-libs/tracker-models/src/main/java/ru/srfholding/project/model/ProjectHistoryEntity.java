package ru.srfholding.project.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;
import java.util.UUID;

import static ru.srfholding.common.constants.SchemaNames.PROJECT_SERVICE_SCHEMA;


/**
 * Сушность истории изменения проекта
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "project_history", schema = PROJECT_SERVICE_SCHEMA)
@EntityListeners(AuditingEntityListener.class)
public class ProjectHistoryEntity {
    /**
     * ID истории изменения проекта
     */
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "project_history_id", nullable = false, insertable = false)
    @Setter(AccessLevel.NONE)
    private UUID projectHistoryId;
    /**
     * ID проекта
     */
    @Column(name = "project_id", nullable = false, insertable = false, updatable = false)
    private UUID projectId;
    /**
     * Проект
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", referencedColumnName = "project_id")
    private ProjectEntity project;
    /**
     * Название поля
     */
    @Column(name = "field_name", length = 100)
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
    @LastModifiedBy
    @Column(name = "changed_by")
    private UUID changedBy;
    /**
     * Дата изменения
     */
    @LastModifiedDate
    @Column(name = "change_at")
    private OffsetDateTime changeAt;

}
