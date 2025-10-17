package ru.srfholding.trackermodels.task_service.model;

import jakarta.persistence.*;
import lombok.*;
import ru.srfholding.trackermodels.base.BaseEntity;
import ru.srfholding.trackermodels.task_service.constant.SprintStatus;
import ru.srfholding.trackermodels.task_service.converter.SprintStatusConverter;

import java.time.OffsetDateTime;
import java.util.UUID;

import static ru.srfholding.trackermodels.common_constants.SchemaNames.TASK_SERVICE_SCHEMA;

/**
 * Сущность спринта
 */
@Entity
@Table(name = "sprint", schema = TASK_SERVICE_SCHEMA)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SprintEntity extends BaseEntity {
    /**
     * ID спринта
     */
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "sprint_id", insertable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private UUID sprintId;
    /**
     * ID проекта
     */
    @Column(name = "project_id", nullable = false)
    private UUID projectId;
    /**
     * Название спринта
     */
    @Column(name = "sprint_name", nullable = false)
    private String sprintName;
    /**
     * Цель
     */
    @Column(name = "goal")
    private String goal;
    /**
     * Дата начала спринта
     */
    @Column(name = "start_date", nullable = false)
    private OffsetDateTime startDate;
    /**
     * Дата окончания
     */
    @Column(name = "end_date", nullable = false)
    private OffsetDateTime endDate;
    /**
     * Статус спринта
     */
    @Column(name = "status_code")
    @Convert(converter = SprintStatusConverter.class)
    private SprintStatus sprintStatus;
    /**
     * Планируемое кол-во стори-поинтов
     */
    @Column(name = "planning_story_points")
    private Integer planningStoryPoints;
    /**
     * Завершенные стори-поинты
     */
    @Column(name = "completed_story_points")
    private Integer completedStoryPoints;

}
