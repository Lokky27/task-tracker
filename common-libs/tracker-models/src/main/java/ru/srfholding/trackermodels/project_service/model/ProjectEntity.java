package ru.srfholding.trackermodels.project_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.srfholding.trackermodels.base.BaseEntity;
import ru.srfholding.trackermodels.project_service.constant.ProjectPriority;
import ru.srfholding.trackermodels.project_service.constant.ProjectStatus;
import ru.srfholding.trackermodels.project_service.constant.VisibilityCode;
import ru.srfholding.trackermodels.project_service.converter.ProjectPriorityConverter;
import ru.srfholding.trackermodels.project_service.converter.ProjectStatusConverter;
import ru.srfholding.trackermodels.project_service.converter.VisibilityConverter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import static lombok.AccessLevel.NONE;
import static ru.srfholding.trackermodels.common_constants.SchemaNames.PROJECT_SERVICE_SCHEMA;

/**
 * Сущность Проекта
 */
@Getter
@Setter
@Entity
@Table(name = "project", schema = PROJECT_SERVICE_SCHEMA)
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class ProjectEntity extends BaseEntity {
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
    @Column(name = "project_name", nullable = false,length = 100)
    private String projectName;
    /**
     * Описание проекта
     */
    @Column(name = "description")
    private String description;
    /**
     * Статус проекта
     */
    @Column(name = "project_status")
    @Convert(converter = ProjectStatusConverter.class)
    private ProjectStatus statusCode;
    /**
     * ID Владельца проекта
     */
    @Column(name = "owner_id", nullable = false)
    private UUID ownerId;
    /**
     * Видимость (1-открытый, 2-закрытый)
     */
    @Column(name = "visibility")
    @Convert(converter = VisibilityConverter.class)
    private VisibilityCode visibility;
    /**
     * Приоритетность проекта
     */
    @Column(name = "project_priority")
    @Convert(converter = ProjectPriorityConverter.class)
    private ProjectPriority projectPriority;
    /**
     * Дата начала проекта
     */
    @Column(name = "start_date")
    private OffsetDateTime startDate;
    /**
     * Дедайн проекта
     */
    @Column(name = "deadline")
    private OffsetDateTime deadline;
    /**
     * Запланированные часы
     */
    @Column(name = "estimated_hours")
    private Double estimatedHours;
    /**
     * Фактически потраченных часов
     */
    @Column(name = "actual_hours")
    private Double actualHours;
    /**
     * Бюджет проекта
     */
    @Column(name = "budget")
    private BigDecimal budget;
    /**
     * Поддержка публичной ссылки
     */
    @Column(name = "allow_public_links")
    private Boolean allowPublicLinks;
    /**
     * Требуется подтвержение задач
     */
    @Column(name = "required_task_approval")
    private Boolean requiredTaskApproval;
    /**
     * Время удаления
     */
    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;
}
